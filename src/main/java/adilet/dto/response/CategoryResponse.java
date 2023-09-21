package adilet.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Setter
@Getter
public class CategoryResponse {
    private Long id;
    private String name;


    public CategoryResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
