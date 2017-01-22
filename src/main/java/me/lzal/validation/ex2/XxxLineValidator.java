package me.lzal.validation.ex2;

import javaslang.control.Try;
import me.lzal.validation.ex2.validators.EndsWithValidator;
import me.lzal.validation.ex2.validators.FieldNotEmptyValidator;
import me.lzal.validation.ex2.validators.FieldNumberValidator;
import me.lzal.validation.ex2.validators.NumericalFieldValidator;
import me.lzal.validation.ex2.validators.StartsWithValidator;
import me.lzal.validation.ex2.validators.ZipCodeValidator;

class XxxLineValidator {

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

}
