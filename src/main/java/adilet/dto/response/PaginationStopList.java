package adilet.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Builder
@Setter
@Getter
public class PaginationStopList {
    private List<StopListResponse> stopListResponses;
    private int currentPage;
    private int size;

    public PaginationStopList(List<StopListResponse> stopListResponses, int currentPage, int size) {
        this.stopListResponses = stopListResponses;
        this.currentPage = currentPage;
        this.size = size;
    }
}
