package me.lzal.validation.ex2;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Line {

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
        return getFields().size();
    }

    private List<Field> getFields() {
        String substring = line.substring(1, line.length() - 1);
        return Arrays.stream(substring.split("\\" + XxxLineValidator.DELIMITER))
            .map(Field::new)
            .collect(Collectors.toList());
    }

    public Field getField(int fieldIndex) {
        return getFields().get(fieldIndex);
    }

    public class Field {

        private final String text;

        public Field(String s) {
            this.text = s;
        }

        public Field trim() {
            return new Field(text.trim());
        }

        public boolean isEmpty() {
            return text.isEmpty();
        }

        public String getText() {
            return text;
        }
    }
}
