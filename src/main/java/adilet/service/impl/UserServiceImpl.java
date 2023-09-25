package adilet.service.impl;

import adilet.dto.SimpleResponse;
import adilet.dto.request.UserRequest;
import adilet.dto.response.UserResponse;
import adilet.entity.Restaurant;
import adilet.entity.User;
import adilet.enums.Role;
import adilet.exception.AlreadyExistException;
import adilet.exception.BadCredentialException;
import adilet.exception.BadRequestException;
import adilet.exception.NotFoundException;
import adilet.repository.RestaurantRepository;
import adilet.repository.UserRepository;
import adilet.security.jwt.JwtService;
import adilet.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public SimpleResponse userApp(UserRequest request) {
        Boolean exists = userRepository.existsByEmail(request.getEmail());
        check(request);
        if (!exists) {
            User user = new User();
            user.setFirstname(request.getFirstName());
            user.setLastname(request.getFirstName());
            user.setDateOfBirth(request.getDateOfBirth());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setPhoneNumber(request.getPhoneNumber());
            user.setExperience(request.getExperience());
            user.setRole(request.getRole());
            userRepository.save(user);
        } else {
            return SimpleResponse.builder().httpStatus(HttpStatus.CONFLICT).message("Email already exist!").build();
        }
        return null;
    }

    @Override
    public SimpleResponse saveUserByAdmin(UserRequest request) {
        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId()).orElseThrow(() -> new NotFoundException("Restaurant with id: " + request.getRestaurantId() + " is no exist!"));
        Boolean exists = userRepository.existsByEmail(request.getEmail());
        List<UserResponse> users = userRepository.getAllUsers(restaurant.getId());
        check(request);
        if (!exists) {
            if (users.size() <= 15) {
                User user = new User();
                user.setFirstname(request.getFirstName());
                user.setLastname(request.getLastName());
                user.setDateOfBirth(request.getDateOfBirth());
                user.setEmail(request.getEmail());
                user.setPassword(passwordEncoder.encode(request.getPassword()));
                user.setPhoneNumber(request.getPhoneNumber());
                user.setExperience(request.getExperience());
                user.setRole(request.getRole());
                user.setRestaurant(restaurant);
                userRepository.save(user);
                return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("User with id: " + user.getId() + " is saved").build();
            } else {
                return SimpleResponse.builder().httpStatus(HttpStatus.BAD_REQUEST).message("No vacancy").build();
            }
        } else {
            return SimpleResponse.builder().httpStatus(HttpStatus.CONFLICT).message("Already exist email").build();
        }
    }

    @Override
    public List<UserResponse> jobApplication(Long id, String word) {
        Restaurant restaurant = restaurantRepository.findById(
                restaurantRepository.findAll().get(0).getId())
                .orElseThrow(() -> new NotFoundException("Restaurant with no exist"));
        if (word.equalsIgnoreCase("Vacancy")) {
            return userRepository.getAllApp();
        } else if (word.equalsIgnoreCase("accept")) {
            List<UserResponse> users = userRepository.getAllUsers(restaurant.getId());
            if (users.size() <= 15) {
                assignUserToRest(id, restaurantRepository.findAll().get(0).getId());
            } else
                throw new BadCredentialException("No vacancy");
        } else if (word.equalsIgnoreCase("cancel")) {
            deleteById(id);
        } else {
            throw new BadCredentialException("User id or keyWord not matched!");
        }
        return null;
    }

    @Override
    public SimpleResponse assignUserToRest(Long userId, Long restId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User with id: " + userId + " is no exist!"));
        Restaurant rest = restaurantRepository.findById(restId).orElseThrow(() -> new NotFoundException("Restaurant with id:" + restId + "is no exist"));
        user.setRestaurant(rest);
        rest.addUser(user);
        userRepository.save(user);
        restaurantRepository.save(rest);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("User with id:" + user.getId() + " is successfully assigned!").build();

    }

    @Override
    public UserResponse getById(Long userId) {
        return userRepository.getUserById(userId).orElseThrow(() -> new NotFoundException("User with id: " + userId + " is no exist!"));
    }

    @Override
    public List<UserResponse> getAll(Long restId) {
        restaurantRepository.findById(restId).orElseThrow(()->new NotFoundException("Restaurant with id: "+restId+" is no exist!"));
        return userRepository.getAllUsers(restId);
    }

    @Override
    public SimpleResponse update(Long userId, UserRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User with id: " + userId + " is no exist!"));
        List<User> all = userRepository.findAll();
        all.remove(user);
        for (User user1 : all) {
            if (!user1.getEmail().equals(request.getEmail())) {
                user.setFirstname(request.getFirstName());
                user.setLastname(request.getLastName());
                user.setDateOfBirth(request.getDateOfBirth());
                user.setEmail(request.getEmail());
                user.setPassword(request.getPassword());
                user.setPhoneNumber(request.getPhoneNumber());
                user.setExperience(request.getExperience());
                userRepository.save(user);
                return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("User with id: " + userId + " is updated!").build();
            } else {
                return SimpleResponse.builder().httpStatus(HttpStatus.FORBIDDEN).message("Email is already exists!").build();
            }
        }
        return null;
    }

    @Override
    public SimpleResponse deleteById(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User with id: " + userId + " is no exist"));
        userRepository.deleteById(userId);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("User with id: " + userId + " is deleted!").build();
    }

    private void check(UserRequest request) {
        Boolean exists = userRepository.existsByPhoneNumber(request.getPhoneNumber());
        if (exists) {
            throw new AlreadyExistException("User with phone number: " + request.getPhoneNumber() + " is already exist!");
        }
        if (!request.getPhoneNumber().startsWith("+996")) {
            throw new BadRequestException("Phone number should starts with +996");
        }
        int year = LocalDate.now().minusYears(request.getDateOfBirth().getYear()).getYear();
        if (request.getRole().equals(Role.CHEF)) {
            if (year <= 25 || year >= 45 && request.getExperience() <= 2) {
                throw new BadRequestException("Chef's years old should be between 25-45 and experience>=2");
            }
        } else if (request.getRole().equals(Role.WAITER)) {
            if (year <= 18 || year >= 30 && request.getExperience() <= 1) {
                throw new BadRequestException("Waiter's years old should be between 18-30 and experience>=1");
            }
        }
    }
}
