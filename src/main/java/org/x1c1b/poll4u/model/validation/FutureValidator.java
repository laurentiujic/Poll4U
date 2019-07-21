package org.x1c1b.poll4u.model.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

public class FutureValidator implements ConstraintValidator<Future, Date> {

    @Override
    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {

        return new Date().before(date);
    }
}
