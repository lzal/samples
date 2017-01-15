package me.lzal.validation;

import static me.lzal.validation.Validator.DELIMITER;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import me.lzal.validation.exceptions.EmptyFieldException;
import me.lzal.validation.exceptions.ExpectingNumericalFieldException;
import me.lzal.validation.exceptions.IncorrectFormatException;
import me.lzal.validation.exceptions.IncorrectNumberOfFieldsException;
import me.lzal.validation.exceptions.IncorrectZipCodeFormatException;
import org.junit.Test;

import java.util.StringJoiner;

public class ValidatorTest {

    @Test
    public void shouldPassTheLine() throws Exception {
        String line = new StringJoiner(DELIMITER, DELIMITER, DELIMITER)
            .add("John").add("Smith").add("23").add("43-603")
            .toString();

        new Validator().validate(line);
    }

    @Test
    public void shouldFailTheLineDoesNotStartWithDelimiter() throws Exception {
        String line = new StringJoiner(DELIMITER)
            .add("John").add("Smith").add("23").add("43-603").add(DELIMITER)
            .toString();

        assertThatThrownBy(() -> new Validator().validate(line))
            .isInstanceOf(IncorrectFormatException.class);
    }

    @Test
    public void shouldFailTheLineDoesNotEndWithDelimiter() throws Exception {
        String line = new StringJoiner(DELIMITER)
            .add(DELIMITER).add("John").add("Smith").add("23").add("43-603")
            .toString();

        assertThatThrownBy(() -> new Validator().validate(line))
            .isInstanceOf(IncorrectFormatException.class);
    }

    @Test
    public void shouldFailTheLineMissingField() throws Exception {
        String line = new StringJoiner(DELIMITER, DELIMITER, DELIMITER)
            .add("John").add("Smith").add("43-603")
            .toString();

        assertThatThrownBy(() -> new Validator().validate(line))
            .isInstanceOf(IncorrectNumberOfFieldsException.class);
    }

    @Test
    public void shouldFailTheLineToManyFields() throws Exception {
        String line = new StringJoiner(DELIMITER, DELIMITER, DELIMITER)
            .add("John").add("Smith").add("23").add("43-603").add("DSA")
            .toString();

        assertThatThrownBy(() -> new Validator().validate(line))
            .isInstanceOf(IncorrectNumberOfFieldsException.class);
    }

    @Test
    public void shouldFailTheLineNotNumericalField() throws Exception {
        String line = new StringJoiner(DELIMITER, DELIMITER, DELIMITER)
            .add("John").add("Smith").add("23X").add("43-603")
            .toString();

        assertThatThrownBy(() -> new Validator().validate(line))
            .isInstanceOf(ExpectingNumericalFieldException.class);
    }

    @Test
    public void shouldFailTheLineWrongZipCodeFormat() throws Exception {
        String line = new StringJoiner(DELIMITER, DELIMITER, DELIMITER)
            .add("John").add("Smith").add("23").add("423-63")
            .toString();

        assertThatThrownBy(() -> new Validator().validate(line))
            .isInstanceOf(IncorrectZipCodeFormatException.class);
    }

    @Test
    public void shouldFailTheNameIsEmpty1() throws Exception {
        String line = new StringJoiner(DELIMITER, DELIMITER, DELIMITER)
            .add("").add("Smith").add("23").add("42-630")
            .toString();

        assertThatThrownBy(() -> new Validator().validate(line))
            .isInstanceOf(EmptyFieldException.class);

    }

    @Test
    public void shouldFailTheNameIsEmpty2() throws Exception {
        String line = new StringJoiner(DELIMITER, DELIMITER, DELIMITER)
            .add(" ").add("Smith").add("23").add("42-630")
            .toString();

        assertThatThrownBy(() -> new Validator().validate(line))
            .isInstanceOf(EmptyFieldException.class);

    }

    @Test
    public void shouldFailTheSurnameIsEmpty1() throws Exception {
        String line = new StringJoiner(DELIMITER, DELIMITER, DELIMITER)
            .add("John").add("").add("23").add("42-630")
            .toString();

        assertThatThrownBy(() -> new Validator().validate(line))
            .isInstanceOf(EmptyFieldException.class);

    }

    @Test
    public void shouldFailTheSurnameIsEmpty2() throws Exception {
        String line = new StringJoiner(DELIMITER, DELIMITER, DELIMITER)
            .add("John").add(" ").add("23").add("42-630")
            .toString();

        assertThatThrownBy(() -> new Validator().validate(line))
            .isInstanceOf(EmptyFieldException.class);

    }
}