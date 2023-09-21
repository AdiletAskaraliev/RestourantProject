package adilet.dto.response;

import adilet.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class AuthenticationResponse {
    private String token;
    private String firstname;
    private String lastname;
    private String email;
    private Role role;


    public AuthenticationResponse(String token, String firstname, String lastname, String email, Role role) {
        this.token = token;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.role = role;
    }
}
