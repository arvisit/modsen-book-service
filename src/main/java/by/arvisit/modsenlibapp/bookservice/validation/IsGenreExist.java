package by.arvisit.modsenlibapp.bookservice.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import by.arvisit.modsenlibapp.bookservice.validation.provider.IsGenreExistValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ FIELD, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = IsGenreExistValidator.class)
public @interface IsGenreExist {

    String message() default "{by.arvisit.modsenlibapp.bookservice.validation.IsGenreExist.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
