package com.editor;

public class Line {
    private int lineNumber;
    private String text;
    private int maxLength;

    public Line(int lineNumber, String text, int maxLength) {
        this.lineNumber = lineNumber;
        this.maxLength = maxLength;
        this.text = text.substring(0, Math.min(text.length(), maxLength));
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text.substring(0, Math.min(text.length(), maxLength));
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public String[] toWordsArray() {
        // The regular expression '\\W+' matches all non-alphabetic characters.
        return text.split("\\W+");
    }

    public void printLine(boolean isLineNumberVisible) {
        if (isLineNumberVisible) {
            System.out.println(lineNumber + ". " + this.text);
        } else System.out.println(text);
    }
}
