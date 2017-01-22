package me.lzal.validation.ex2.validators;

import javaslang.control.Try;
import me.lzal.validation.ex1.exceptions.IncorrectNumberOfFieldsException;
import me.lzal.validation.ex2.Line;
import me.lzal.validation.ex2.Validator;

public class FieldNumberValidator implements Validator<Line> {

    private int expectedNoOfFields;

    public FieldNumberValidator(int expectedNoOfFields) {
        this.expectedNoOfFields = expectedNoOfFields;
    }

    @Override
    public Try<Line> validate(Line line) {
        return Try.of(() -> {
            if (line.fieldsSize() != expectedNoOfFields) {
                throw new IncorrectNumberOfFieldsException();
            }
            return line;
        });
    }
}
