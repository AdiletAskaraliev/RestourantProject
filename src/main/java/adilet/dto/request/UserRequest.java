package adilet.dto.request;

import adilet.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
@Getter
@Setter
@Builder
public class UserRequest {
    private Long restaurantId;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    @Email(message = "Email should be valid")
    private String email;
    @Length(min = 4, max = 15, message = "password size should be between 4 and 15")
    private String password;
    private String phoneNumber;
    private int experience;
    private Role role;

    public UserRequest(Long restaurantId, String firstName, String lastName, LocalDate dateOfBirth, String email, String password, String phoneNumber, int experience, Role role) {
        this.restaurantId = restaurantId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.experience = experience;
        this.role = role;
    }
}
