package org.x1c1b.poll4u.model.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Date;

@Constraint(validatedBy = FutureValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD } )
@Retention(RetentionPolicy.RUNTIME)
public @interface Future {

    String message() default "{org.x1c1b.poll4u.model.validation.Future.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
