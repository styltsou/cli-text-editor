package com.editor;

import java.util.ArrayList;

public class SearchData {
    private ArrayList<Integer> lineOccurrences;
    private int diskAccesses;

    public SearchData() {
        this.lineOccurrences = new ArrayList<>();
        this.diskAccesses = 0;
    }

    public ArrayList<Integer> getLineOccurrences() {
        return lineOccurrences;
    }

    public void setLineOccurrences(ArrayList<Integer> lineOccurrences) {
        this.lineOccurrences = lineOccurrences;
    }

    public int getDiskAccesses() {
        return diskAccesses;
    }

    public void setDiskAccesses(int diskAccesses) {
        this.diskAccesses = diskAccesses;
    }
}
