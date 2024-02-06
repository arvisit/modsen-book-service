package by.arvisit.modsenlibapp.bookservice.validation.provider;

import by.arvisit.modsenlibapp.bookservice.validation.Isbn;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IsbnValidator implements ConstraintValidator<Isbn, String> {

    private static final String ISBN_13_REGEX = "^(\\d+-\\d+-\\d+-\\d+-[\\dX])$";
    private static final int ISBN_13_LENGTH = 17;
    private static final String ISBN_10_REGEX = "^(\\d+-\\d+-\\d+-[\\dX])$";
    private static final int ISBN_10_LENGTH = 13;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return (value.matches(ISBN_13_REGEX) && value.length() == ISBN_13_LENGTH)
                || (value.matches(ISBN_10_REGEX) && value.length() == ISBN_10_LENGTH);
    }

}
