package adilet.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ChequeRequest {
    private Long userId;

    private List<Long> menuItemsId;

    public ChequeRequest(Long userId, List<Long> menuItemsId) {
        this.userId = userId;
        this.menuItemsId = menuItemsId;
    }
}
