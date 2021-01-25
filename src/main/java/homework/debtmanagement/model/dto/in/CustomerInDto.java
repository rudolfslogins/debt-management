package homework.debtmanagement.model.dto.in;

import homework.debtmanagement.validator.CustomerExistsConstraint;
import homework.debtmanagement.validator.EmailExistsConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
@CustomerExistsConstraint
@EmailExistsConstraint
@AllArgsConstructor
@NoArgsConstructor
public class CustomerInDto {

    @NotEmpty(message = "Name must not be empty")
    String name;

    @NotEmpty(message = "Surname must not be empty")
    String surname;

    @NotEmpty(message = "Country must not be empty")
    String country;

    @Email
    @NotEmpty(message = "Email must not be empty")
    String email;

    @NotEmpty(message = "Password must not be empty")
    String password;

}
