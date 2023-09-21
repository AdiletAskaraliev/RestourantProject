package adilet.service;

import adilet.dto.SimpleResponse;
import adilet.dto.request.RestaurantRequest;
import adilet.dto.response.RestaurantResponse;
import adilet.entity.Restaurant;

import java.util.List;

public interface RestaurantService {
    List<Restaurant> findAll();

    RestaurantResponse save(RestaurantRequest restaurantRequest);

    RestaurantResponse findById(Long resId);

    SimpleResponse update(Long resId, RestaurantRequest restaurantRequest);

    SimpleResponse delete(Long resId);
}
