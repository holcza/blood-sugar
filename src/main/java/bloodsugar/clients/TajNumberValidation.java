package bloodsugar.clients;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = TajNumberValidator.class)
public @interface TajNumberValidation {

    String message() default "Invalid TAJ number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
