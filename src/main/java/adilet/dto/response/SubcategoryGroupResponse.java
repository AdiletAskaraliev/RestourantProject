package adilet.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Builder
public class SubcategoryGroupResponse {
    private String categoryName;
    private List<String> subcategoryName;

    public SubcategoryGroupResponse(String categoryName, List<String> subcategoryName) {
        this.categoryName = categoryName;
        this.subcategoryName = subcategoryName;
    }
}


