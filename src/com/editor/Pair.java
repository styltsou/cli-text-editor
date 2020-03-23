package com.editor;

public class Pair implements Comparable<Pair> {
    private String word;
    private int lineOccurrence;

    public Pair(String word, int lineOccurrence) {
        this.word = word;
        this.lineOccurrence = lineOccurrence;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getLineOccurrence() {
        return lineOccurrence;
    }

    public void setLineOccurrence(int lineOccurrence) {
        this.lineOccurrence = lineOccurrence;
    }

    @Override
    public int compareTo(Pair pair) {
        return word.compareTo(pair.word);
    }
}
