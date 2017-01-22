package me.lzal.validation.ex2;

import static me.lzal.validation.ex2.XxxLineValidator.DELIMITER;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import me.lzal.validation.ex1.exceptions.EmptyFieldException;
import me.lzal.validation.ex1.exceptions.ExpectingNumericalFieldException;
import me.lzal.validation.ex1.exceptions.IncorrectFormatException;
import me.lzal.validation.ex1.exceptions.IncorrectNumberOfFieldsException;
import me.lzal.validation.ex1.exceptions.IncorrectZipCodeFormatException;
import org.junit.Test;

import java.util.StringJoiner;

public class XxxLineValidatorTest {

    @Test
    public void shouldPassTheLine() throws Exception {
        String line = new StringJoiner(DELIMITER, DELIMITER, DELIMITER)
            .add("John").add("Smith").add("23").add("43-603")
            .toString();

        new XxxLineValidator().validate(line);
    }

    @Test
    public void shouldFailTheLineDoesNotStartWithDelimiter() throws Exception {
        String line = new StringJoiner(DELIMITER)
            .add("John").add("Smith").add("23").add("43-603").add(DELIMITER)
            .toString();

        assertThatThrownBy(() -> new XxxLineValidator().validate(line))
            .isInstanceOf(IncorrectFormatException.class);
    }

    @Test
    public void shouldFailTheLineDoesNotEndWithDelimiter() throws Exception {
        String line = new StringJoiner(DELIMITER)
            .add(DELIMITER).add("John").add("Smith").add("23").add("43-603")
            .toString();

        assertThatThrownBy(() -> new XxxLineValidator().validate(line))
            .isInstanceOf(IncorrectFormatException.class);
    }

    @Test
    public void shouldFailTheLineMissingField() throws Exception {
        String line = new StringJoiner(DELIMITER, DELIMITER, DELIMITER)
            .add("John").add("Smith").add("43-603")
            .toString();

        assertThatThrownBy(() -> new XxxLineValidator().validate(line))
            .isInstanceOf(IncorrectNumberOfFieldsException.class);
    }

    @Test
    public void shouldFailTheLineToManyFields() throws Exception {
        String line = new StringJoiner(DELIMITER, DELIMITER, DELIMITER)
            .add("John").add("Smith").add("23").add("43-603").add("DSA")
            .toString();

        assertThatThrownBy(() -> new XxxLineValidator().validate(line))
            .isInstanceOf(IncorrectNumberOfFieldsException.class);
    }

    @Test
    public void shouldFailTheLineNotNumericalField() throws Exception {
        String line = new StringJoiner(DELIMITER, DELIMITER, DELIMITER)
            .add("John").add("Smith").add("23X").add("43-603")
            .toString();

        assertThatThrownBy(() -> new XxxLineValidator().validate(line))
            .isInstanceOf(ExpectingNumericalFieldException.class);
    }

    @Test
    public void shouldFailTheLineWrongZipCodeFormat() throws Exception {
        String line = new StringJoiner(DELIMITER, DELIMITER, DELIMITER)
            .add("John").add("Smith").add("23").add("423-63")
            .toString();

        assertThatThrownBy(() -> new XxxLineValidator().validate(line))
            .isInstanceOf(IncorrectZipCodeFormatException.class);
    }

    @Test
    public void shouldFailTheNameIsEmpty1() throws Exception {
        String line = new StringJoiner(DELIMITER, DELIMITER, DELIMITER)
            .add("").add("Smith").add("23").add("42-630")
            .toString();

        assertThatThrownBy(() -> new XxxLineValidator().validate(line))
            .isInstanceOf(EmptyFieldException.class);

    }

    @Test
    public void shouldFailTheNameIsEmpty2() throws Exception {
        String line = new StringJoiner(DELIMITER, DELIMITER, DELIMITER)
            .add(" ").add("Smith").add("23").add("42-630")
            .toString();

        assertThatThrownBy(() -> new XxxLineValidator().validate(line))
            .isInstanceOf(EmptyFieldException.class);

    }

    @Test
    public void shouldFailTheSurnameIsEmpty1() throws Exception {
        String line = new StringJoiner(DELIMITER, DELIMITER, DELIMITER)
            .add("John").add("").add("23").add("42-630")
            .toString();

        assertThatThrownBy(() -> new XxxLineValidator().validate(line))
            .isInstanceOf(EmptyFieldException.class);

    }

    @Test
    public void shouldFailTheSurnameIsEmpty2() throws Exception {
        String line = new StringJoiner(DELIMITER, DELIMITER, DELIMITER)
            .add("John").add(" ").add("23").add("42-630")
            .toString();

        assertThatThrownBy(() -> new XxxLineValidator().validate(line))
            .isInstanceOf(EmptyFieldException.class);

    }
}