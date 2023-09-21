package adilet.dto.request;

import adilet.enums.RestType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RestaurantRequest {
    private String name;
    private String location;
    private RestType restType;
    private int service;

    public RestaurantRequest(String name, String location, RestType restType, int service) {
        this.name = name;
        this.location = location;
        this.restType = restType;
        this.service = service;
    }
}
