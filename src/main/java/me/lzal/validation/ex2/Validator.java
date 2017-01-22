package me.lzal.validation.ex2;

import static java.lang.Integer.parseInt;

import javaslang.control.Try;
import me.lzal.validation.ex1.exceptions.EmptyFieldException;
import me.lzal.validation.ex1.exceptions.ExpectingNumericalFieldException;
import me.lzal.validation.ex1.exceptions.IncorrectFormatException;
import me.lzal.validation.ex1.exceptions.IncorrectNumberOfFieldsException;
import me.lzal.validation.ex1.exceptions.IncorrectZipCodeFormatException;

import java.util.regex.Pattern;

class Validator {

    static final String DELIMITER = "|";

    private static final int FIELD_NAME_INDEX = 0;
    private static final int FIELD_SURNAME_INDEX = 1;
    private static final int FIELD_AGE_INDEX = 2;
    private static final int FIELD_ZIP_CODE_INDEX = 3;

    // validate :: Line -> Try<Line>
    //
    // -- wartosc Success<Line> lub Failure<Exception> expectedNoOfFields to jest chyba najlepsza opcja,
    //    gdyz zwracam rodzaj Bledu (exception) lub linie, ktora dalej mozna processowac.
    public void validate(String line) {
        // A -> m A
        // pure A >>= validate1 >>= validate2 >>= validate3
        Try<Line> result = Try.of(() -> new Line(line))
            .flatMap(new StartsWithValidator(DELIMITER)::validate)
            .flatMap(new EndsWithValidator(DELIMITER)::validate)
            .flatMap(new FieldNumberValidator(4)::validate)
            .flatMap(new FieldNotEmptyValidator(FIELD_NAME_INDEX)::validate)
            .flatMap(new FieldNotEmptyValidator(FIELD_SURNAME_INDEX)::validate)
            .flatMap(new NumericalFieldValidator(FIELD_AGE_INDEX)::validate)
            .flatMap(new ZipCodeValidator(FIELD_ZIP_CODE_INDEX)::validate);

        if (result.isFailure()) {
            Throwable r = result.failed().get();
            if (r instanceof RuntimeException) {
                throw (RuntimeException) r;
            }
            throw new RuntimeException(r);
        }
    }

    private class Line {

        private final String line;

        public Line(String line) {
            this.line = line;
        }

        public boolean startsWith(String c) {
            return line.startsWith(c);
        }

        public boolean endsWith(String c) {
            return line.endsWith(c);
        }

        public int fieldsSize() {
            String[] split = getFields();
            return split.length;
        }

        private String[] getFields() {
            String substring = line.substring(1, line.length() - 1);
            return substring.split("\\" + DELIMITER);
        }

        public String getField(int fieldIndex) {
            return getFields()[fieldIndex];
        }
    }

    private class StartsWithValidator {

        private String delimiter;

        public StartsWithValidator(String delimiter) {
            this.delimiter = delimiter;
        }

        public Try<Line> validate(Line line) {
            return Try.of(() -> {
                if (!line.startsWith(delimiter)) {
                    throw new IncorrectFormatException();
                }
                return line;
            });
        }
    }

    private class EndsWithValidator {

        private String delimiter;

        public EndsWithValidator(String delimiter) {
            this.delimiter = delimiter;
        }

        public Try<Line> validate(Line line) {
            return Try.of(() -> {
                if (!line.endsWith(delimiter)) {
                    throw new IncorrectFormatException();
                }
                return line;
            });
        }
    }

    private class FieldNumberValidator {

        private int expectedNoOfFields;

        public FieldNumberValidator(int expectedNoOfFields) {
            this.expectedNoOfFields = expectedNoOfFields;
        }

        public Try<Line> validate(Line line) {
            return Try.of(() -> {
                if (line.fieldsSize() != expectedNoOfFields) {
                    throw new IncorrectNumberOfFieldsException();
                }
                return line;
            });
        }
    }

    private class FieldNotEmptyValidator {

        private int fieldIndex;

        public FieldNotEmptyValidator(int fieldIndex) {
            this.fieldIndex = fieldIndex;
        }

        public Try<Line> validate(Line line) {
            return Try.of(() -> {
                if (line.getField(fieldIndex).trim().isEmpty()) {
                    throw new EmptyFieldException();
                }
                return line;
            });
        }
    }

    private class NumericalFieldValidator {

        private int fieldIndex;

        public NumericalFieldValidator(int fieldIndex) {
            this.fieldIndex = fieldIndex;
        }

        public Try<Line> validate(Line line) {
            return Try.of(() -> {
                try {
                    parseInt(line.getField(fieldIndex));
                } catch (NumberFormatException e) {
                    throw new ExpectingNumericalFieldException();
                }
                return line;
            });
        }
    }

    private static class ZipCodeValidator {

        private static final String REGEX = "\\d{2}-\\d{3}";
        private int fieldIndex;

        public ZipCodeValidator(int fieldIndex) {
            this.fieldIndex = fieldIndex;
        }

        public Try<Line> validate(Line line) {
            return Try.of(() -> {
                String zipCode = line.getField(fieldIndex);
                Pattern p = Pattern.compile(REGEX);
                if (!p.asPredicate().test(zipCode)) {
                    throw new IncorrectZipCodeFormatException();
                }
                return line;
            });
        }
    }
}
