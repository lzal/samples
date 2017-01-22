package me.lzal.validation.ex2.validators;

import javaslang.control.Try;
import me.lzal.validation.ex1.exceptions.IncorrectZipCodeFormatException;
import me.lzal.validation.ex2.Line;
import me.lzal.validation.ex2.Validator;

import java.util.function.Predicate;
import java.util.regex.Pattern;

public class ZipCodeValidator implements Validator<Line> {

    private static final String REGEX = "\\d{2}-\\d{3}";
    private final Predicate<String> zipCodePattern;
    private final int fieldIndex;

    public ZipCodeValidator(int fieldIndex) {
        this.fieldIndex = fieldIndex;
        this.zipCodePattern = Pattern.compile(REGEX).asPredicate();
    }

    @Override
    public Try<Line> validate(Line line) {
        return Try.of(() -> {
            if (!zipCodePattern.test(line.getField(fieldIndex).getText())) {
                throw new IncorrectZipCodeFormatException();
            }
            return line;
        });
    }
}
