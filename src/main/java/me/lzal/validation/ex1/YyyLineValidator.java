package me.lzal.validation.ex1;

import static java.lang.Integer.parseInt;

import me.lzal.validation.ex1.exceptions.EmptyFieldException;
import me.lzal.validation.ex1.exceptions.ExpectingNumericalFieldException;
import me.lzal.validation.ex1.exceptions.IncorrectFormatException;
import me.lzal.validation.ex1.exceptions.IncorrectNumberOfFieldsException;
import me.lzal.validation.ex1.exceptions.IncorrectZipCodeFormatException;

import java.util.regex.Pattern;

class YyyLineValidator {

    public static final String DELIMITER = "|";

    private static final int FIELD_NAME_INDEX = 0;
    private static final int FIELD_SURNAME_INDEX = 1;
    private static final int FIELD_AGE_INDEX = 2;
    private static final int FIELD_ZIP_CODE_INDEX = 3;

    public void validate(String line) {
        if (!line.startsWith(DELIMITER) || !line.endsWith(DELIMITER)) {
            throw new IncorrectFormatException();
        }
        String substring = line.substring(1, line.length() - 1);
        String[] split = substring.split("\\" + DELIMITER);
        if (split.length != 4) {
            throw new IncorrectNumberOfFieldsException();
        }

        if (split[FIELD_NAME_INDEX].trim().isEmpty()) {
            throw new EmptyFieldException();
        }

        if (split[FIELD_SURNAME_INDEX].trim().isEmpty()) {
            throw new EmptyFieldException();
        }

        try {
            parseInt(split[FIELD_AGE_INDEX]);
        } catch (NumberFormatException e) {
            throw new ExpectingNumericalFieldException();
        }

        String zipCode = split[FIELD_ZIP_CODE_INDEX];
        Pattern p = Pattern.compile("\\d{2}-\\d{3}");
        if (!p.asPredicate().test(zipCode)) {
            throw new IncorrectZipCodeFormatException();
        }

    }
}
