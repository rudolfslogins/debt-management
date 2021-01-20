package homework.debtmanagement.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CustomerExistsValidator.class)
public @interface CustomerExistsConstraint {

    String message() default "Customer with this NameSurnameCountryEmail already exists";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
