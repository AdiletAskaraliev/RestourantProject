package adilet.api;

import adilet.dto.SimpleResponse;
import adilet.dto.request.RestaurantRequest;
import adilet.dto.response.RestaurantResponse;
import adilet.entity.Restaurant;
import adilet.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantApi {

    private final RestaurantService restaurantService;

    @GetMapping
    public List<Restaurant> getAll(){
        return restaurantService.findAll();
    }

    @PostMapping
    public RestaurantResponse save(@RequestBody RestaurantRequest restaurantRequest){
        return restaurantService.save(restaurantRequest);
    }

    @GetMapping("/{resId}")
    public RestaurantResponse getById(@PathVariable Long resId){
        return restaurantService.findById(resId);
    }

    @PutMapping("/{resId}")
    public SimpleResponse update(@PathVariable Long resId,
                                 @RequestBody RestaurantRequest restaurantRequest){
        return restaurantService.update(resId, restaurantRequest);
    }

    @DeleteMapping("/{resId}")
    public SimpleResponse delete(@PathVariable Long resId){
        return restaurantService.delete(resId);
    }

}
