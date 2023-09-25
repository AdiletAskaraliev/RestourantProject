package adilet.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class StopListResponse {
    private String reason;
    private LocalDate date;

    public StopListResponse(String reason, LocalDate date) {
        this.reason = reason;
        this.date = date;
    }
}
