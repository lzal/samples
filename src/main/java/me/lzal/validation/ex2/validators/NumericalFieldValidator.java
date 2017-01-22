package me.lzal.validation.ex2.validators;

import static java.lang.Integer.parseInt;

import javaslang.control.Try;
import me.lzal.validation.ex1.exceptions.ExpectingNumericalFieldException;
import me.lzal.validation.ex2.Line;
import me.lzal.validation.ex2.Validator;

public class NumericalFieldValidator implements Validator<Line> {

    private int fieldIndex;

    public NumericalFieldValidator(int fieldIndex) {
        this.fieldIndex = fieldIndex;
    }

    @Override
    public Try<Line> validate(Line line) {
        return Try.of(() -> {
            try {
                parseInt(line.getField(fieldIndex).getText());
            } catch (NumberFormatException e) {
                throw new ExpectingNumericalFieldException();
            }
            return line;
        });
    }
}
