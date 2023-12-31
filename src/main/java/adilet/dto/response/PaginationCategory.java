package adilet.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Setter
@Getter
public class PaginationCategory {
    private List<CategoryResponse> categoryResponses;
    private int currentPage;
    private int size;

    public PaginationCategory(List<CategoryResponse> categoryResponses, int currentPage, int size) {
        this.categoryResponses = categoryResponses;
        this.currentPage = currentPage;
        this.size = size;
    }
}
