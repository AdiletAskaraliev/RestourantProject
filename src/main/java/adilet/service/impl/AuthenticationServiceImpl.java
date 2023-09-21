package adilet.service.impl;

import adilet.dto.request.SignInRequest;
import adilet.dto.request.SignUpRequest;
import adilet.dto.response.AuthenticationResponse;
import adilet.entity.User;
import adilet.enums.Role;
import adilet.exception.AlreadyExistException;
import adilet.exception.BadCredentialException;
import adilet.exception.NotFoundException;
import adilet.repository.UserRepository;
import adilet.security.jwt.JwtService;
import adilet.service.AuthenticationService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthenticationResponse signUp(SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())){
            log.error( "User with email: " + signUpRequest.getEmail() + " already exists!");
            throw new AlreadyExistException(
                    "User with email: " + signUpRequest.getEmail() + " already exists!"
            );
        }
        User user = User.builder()
                .firstname(signUpRequest.getFirstname())
                .lastname(signUpRequest.getLastname())
                .dateOfBirth(signUpRequest.getDateOfBirth())
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .phoneNumber(signUpRequest.getPhoneNumber())
                .role(signUpRequest.getRole())
                .experience(signUpRequest.getExperience())
                .build();

        userRepository.save(user);

        String  token = jwtService.generateToken(user);
        log.info("Successfully generated token");
        return AuthenticationResponse.builder()
                .token(token)
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    @Override
    public AuthenticationResponse signIn(SignInRequest signInRequest) {
        User user = userRepository.getUserByEmail(signInRequest.getEmail())
                .orElseThrow(()-> {
                    log.error("User with email: " + signInRequest.getEmail() + " doesn't exists!");
                    return new NotFoundException(
                            "User with email: " + signInRequest.getEmail() + " doesn't exists!");
                        });

        if (signInRequest.getEmail().isBlank()){
            log.error("Email is blank!");
            throw new BadCredentialException("Email is blank!");
        }

        if (!passwordEncoder.matches(signInRequest.getPassword(), user.getPassword())){
            log.error("Wrong password!");
            throw new BadCredentialException("Wrong password!");
        }

        String token = jwtService.generateToken(user);
        log.info("Successfully generated token");
        return AuthenticationResponse.builder()
                .token(token)
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
    @PostConstruct
    public void saveDefaultAdmin(){
        User user = userRepository.getUserByEmail("admin@gmail.com").orElse(null);
        if (user == null) {
            User admin = new User();
            admin.setEmail("admin@gmail.com");
            admin.setRole(Role.ADMIN);
            admin.setPassword(passwordEncoder.encode("admin00"));
            userRepository.save(admin);
        }
    }
}
