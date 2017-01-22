package me.lzal.validation.ex2;

import javaslang.control.Try;

@FunctionalInterface
public interface Validator<T> {

    Try<T> validate(T line);
}
