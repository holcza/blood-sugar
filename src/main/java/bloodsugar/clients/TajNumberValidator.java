package bloodsugar.clients;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TajNumberValidator implements ConstraintValidator<TajNumberValidation, String> {


    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s.length() != 9) {
            return false;
        }
        for (int i = 0; i < 9; i++) {

            if (!Character.isDigit(s.charAt(i))) {

                return false;
            }
        }
        return true;
    }
}
