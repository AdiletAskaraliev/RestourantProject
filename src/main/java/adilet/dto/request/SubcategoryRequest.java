package adilet.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SubcategoryRequest {
    private String name;

    public SubcategoryRequest() {
    }

    public SubcategoryRequest(String name) {
        this.name = name;
    }
}
