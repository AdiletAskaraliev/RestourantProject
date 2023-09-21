package adilet.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class SubcategoryGroupResponse {
    private Long categoryId;
    private String categoryName;
    private Long subcategoryId;
    private String subcategoryName;

    public SubcategoryGroupResponse(Long categoryId, String categoryName, Long subcategoryId, String subcategoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.subcategoryId = subcategoryId;
        this.subcategoryName = subcategoryName;
    }


}
