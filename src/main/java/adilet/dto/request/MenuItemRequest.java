package adilet.dto.request;

import adilet.validation.PriceValidation;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class MenuItemRequest {
    private String name;
    private String image;
    @PriceValidation
    private BigDecimal price;
    private String description;
    private boolean isVegetarian;

    public MenuItemRequest(String name, String image, BigDecimal price, String description, boolean isVegetarian) {
        this.name = name;
        this.image = image;
        this.price = price;
        this.description = description;
        this.isVegetarian = isVegetarian;
    }
}
