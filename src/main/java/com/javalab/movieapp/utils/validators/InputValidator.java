package com.javalab.movieapp.utils.validators;

import static com.javalab.movieapp.Constants.*;

public class InputValidator {

    public static String validateCrucialParamID(String input) throws IncorrectCrucialParamException {
        if (input == null || input.isEmpty() || !input.matches(NUMBER_REGEX) || input.length() > LONG_MAX_LENGTH) {
            throw new IncorrectCrucialParamException();
        } else {
            return input;
        }
    }

    public static String validateDate(String input) throws InputValidationException{
        if(input == null || input.isEmpty() || !input.matches(DATE_REGEX)){
            throw new InputValidationException();
        } else {
            return input;
        }
    }

    public static String validateEmail(String input) throws InputValidationException {
        if (!input.matches(EMAIL_REGEX)) {
            throw new InputValidationException();
        }
        return validateName(input);
    }

    public static String validateTitle(String input) throws InputValidationException {
        return validate(input, TITLE_MAX_LENGTH);
    }

    public static String validateMovieRate(String input) throws InputValidationException {
        validateNumber(input);
        return validate(input, MOVIE_RATE_MAX_LENGTH);
    }

    public static String validateName(String input) throws InputValidationException {
        return validate(input, STRING_MAX_LENGTH);
    }

    public static String validateLong(String input) throws InputValidationException {
        validateNumber(input);
        return validate(input, LONG_MAX_LENGTH);
    }

    public static String validateInteger(String input) throws InputValidationException {
        validateNumber(input);
        return validate(input, INT_MAX_LENGTH);
    }

    public static String validateNullOrEmpty(String input) throws InputValidationException {
        if (input == null || input.isEmpty()) {
            throw new InputValidationException();
        } else {
            return input;
        }
    }

    private static void validateNumber(String input) throws InputValidationException {
        if (!input.matches(NUMBER_REGEX)) {
            throw new InputValidationException();
        }
    }

    private static String validate(String input, int maxLength) throws InputValidationException {
        if (input == null || input.isEmpty() || input.length() > maxLength) {
            throw new InputValidationException();
        } else {
            return input;
        }
    }
}
