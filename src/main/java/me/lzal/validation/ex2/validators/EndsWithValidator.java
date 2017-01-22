package me.lzal.validation.ex2.validators;

import javaslang.control.Try;
import me.lzal.validation.ex1.exceptions.IncorrectFormatException;
import me.lzal.validation.ex2.Line;
import me.lzal.validation.ex2.Validator;

public class EndsWithValidator implements Validator<Line> {

    private String delimiter;

    public EndsWithValidator(String delimiter) {
        this.delimiter = delimiter;
    }

    @Override
    public Try<Line> validate(Line line) {
        return Try.of(() -> {
            if (!line.endsWith(delimiter)) {
                throw new IncorrectFormatException();
            }
            return line;
        });
    }
}
