package me.lzal.validation.ex2.validators;

import javaslang.control.Try;
import me.lzal.validation.ex1.exceptions.IncorrectFormatException;
import me.lzal.validation.ex2.Line;
import me.lzal.validation.ex2.Validator;

public class StartsWithValidator implements Validator<Line> {

    private String delimiter;

    public StartsWithValidator(String delimiter) {
        this.delimiter = delimiter;
    }

    @Override
    public Try<Line> validate(Line line) {
        return Try.of(() -> {
            if (!line.startsWith(delimiter)) {
                throw new IncorrectFormatException();
            }
            return line;
        });
    }
}
