package adilet.dto.response;

import adilet.entity.MenuItem;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ChequeResponse {
    private Long id;
    private String fullName;
    private List<MenuItem> items;
    private BigDecimal averagePrice;
    private int service;
    private BigDecimal total;

    public ChequeResponse() {
    }

    public ChequeResponse(Long id, String fullName, List<MenuItem> items, BigDecimal averagePrice, int service, BigDecimal total) {
        this.id = id;
        this.fullName = fullName;
        this.items = items;
        this.averagePrice = averagePrice;
        this.service = service;
        this.total = total;
    }
}
