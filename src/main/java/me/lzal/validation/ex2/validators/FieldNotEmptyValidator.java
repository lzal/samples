package me.lzal.validation.ex2.validators;

import javaslang.control.Try;
import me.lzal.validation.ex1.exceptions.EmptyFieldException;
import me.lzal.validation.ex2.Line;
import me.lzal.validation.ex2.Validator;

public class FieldNotEmptyValidator implements Validator<Line> {

    private int fieldIndex;

    public FieldNotEmptyValidator(int fieldIndex) {
        this.fieldIndex = fieldIndex;
    }

    @Override
    public Try<Line> validate(Line line) {
        return Try.of(() -> {
            String field = line.getField(fieldIndex);
            if (field.trim().isEmpty()) {
                throw new EmptyFieldException();
            }
            return line;
        });
    }
}
