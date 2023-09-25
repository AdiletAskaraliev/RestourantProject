package adilet.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class StopListRequest {
    private String reason;
    private LocalDate date;

    public StopListRequest() {
    }

    public StopListRequest(String reason, LocalDate date) {
        this.reason = reason;
        this.date = date;
    }
}
