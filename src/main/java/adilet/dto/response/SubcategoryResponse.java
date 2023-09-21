package adilet.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SubcategoryResponse {
    private Long id;
    private String name;
    private String categoryName;

    public SubcategoryResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public SubcategoryResponse(Long id, String name, String categoryName) {
        this.id = id;
        this.name = name;
        this.categoryName = categoryName;
    }
}
