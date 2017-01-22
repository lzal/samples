package me.lzal.validation.ex2;

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
        String[] split = getFields();
        return split.length;
    }

    private String[] getFields() {
        String substring = line.substring(1, line.length() - 1);
        return substring.split("\\" + XxxLineValidator.DELIMITER);
    }

    public String getField(int fieldIndex) {
        return getFields()[fieldIndex];
    }
}
