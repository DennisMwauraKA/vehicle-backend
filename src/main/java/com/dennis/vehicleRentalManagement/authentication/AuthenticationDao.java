package com.dennis.vehicleRentalManagement.authentication;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class AuthenticationDao {
    @NotEmpty
    @NotBlank
    @Email(message = "Email must be well formatted")
    private String email;
    @NotEmpty
    @NotBlank
    @Length(min = 6)
    private String password;
}
