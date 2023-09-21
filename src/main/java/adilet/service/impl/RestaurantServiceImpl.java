package adilet.service.impl;

import adilet.dto.SimpleResponse;
import adilet.dto.request.RestaurantRequest;
import adilet.dto.response.RestaurantResponse;
import adilet.entity.Restaurant;
import adilet.exception.AlreadyExistException;
import adilet.exception.NotFoundException;
import adilet.repository.RestaurantRepository;
import adilet.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Override
    public List<Restaurant> findAll() {
        return restaurantRepository.findAll();
    }

    @Override
    public RestaurantResponse save(RestaurantRequest restaurantRequest) {
        Restaurant restaurant = new Restaurant();

        if (restaurantRepository.existsByName(restaurantRequest.getName())) {
            throw new AlreadyExistException("Restaurant with name " + restaurantRequest.getName() + " already exists");
        }

        restaurant.setName(restaurantRequest.getName());
        restaurant.setLocation(restaurantRequest.getLocation());
        restaurant.setRestType(restaurantRequest.getRestType());
        restaurant.setService(restaurantRequest.getService());

        log.info("Restaurant successfully saved");
        restaurantRepository.save(restaurant);
        return new RestaurantResponse(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getLocation(),
                restaurant.getRestType(),
                restaurant.getNumberOfEmployees(),
                restaurant.getService()
        );
    }

    @Override
    public RestaurantResponse findById(Long resId) {
        return restaurantRepository.findRestaurantById(resId)
                .orElseThrow(() -> {
                    log.error("Restaurant with id: " + resId + " not found");
                    return new NotFoundException("Restaurant with id: " + resId + " not found");
                });
    }

    @Override
    public SimpleResponse update(Long resId, RestaurantRequest restaurantRequest) {
        Restaurant restaurant = restaurantRepository.findById(resId)
                .orElseThrow(() -> {
                    log.error("Restaurant with id: " + resId + " not found");
                    return new NotFoundException("Restaurant with id: " + resId + " not found");
                });

        restaurant.setName(restaurantRequest.getName());
        restaurant.setLocation(restaurantRequest.getLocation());
        restaurant.setRestType(restaurantRequest.getRestType());
        restaurant.setService(restaurantRequest.getService());

        restaurantRepository.save(restaurant);
        log.info("Restaurant successfully updated");
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully updated")
                .build();
    }

    @Override
    public SimpleResponse delete(Long resId) {
        restaurantRepository.deleteById(resId);

        log.info("Restaurant successfully deleted");

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully deleted")
                .build();
    }


}
