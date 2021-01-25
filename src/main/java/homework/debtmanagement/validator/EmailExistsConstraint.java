package homework.debtmanagement.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailExistsValidator.class)
public @interface EmailExistsConstraint {

    String message() default "Email already registered";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
