package com.editor;

import javax.xml.crypto.dom.DOMCryptoContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class IndexingArray {
    private Pair[] pairs;
    private int minWordSize;
    private int maxWordSize;

    public IndexingArray(int minWordSize, int maxWordSize) {
        this.minWordSize = minWordSize;
        this.maxWordSize = maxWordSize;
    }

    public Pair[] getPairs() {
        return pairs;
    }

    public void setPairs(Pair[] pairs) {
        this.pairs = pairs;
    }

    public int getMinWordSize() {
        return minWordSize;
    }

    public void setMinWordSize(int minWordSize) {
        this.minWordSize = minWordSize;
    }

    public int getMaxWordSize() {
        return maxWordSize;
    }

    public void setMaxWordSize(int maxWordSize) {
        this.maxWordSize = maxWordSize;
    }

    // populates the array (pairs) with data from the editor.
    public void populate(DoublyLinkedList<Line> editorContent) {
        ArrayList<Pair> pairsList = new ArrayList<>();

        // Iterate through editorContent (Doubly Linked List)
        for (int i = 1; i <= editorContent.size(); i++) {
            ArrayList<String> restrictedList;

            String[] wordsArr = editorContent.getNthNode(i).data.toWordsArray();
            // Remove duplicates
            wordsArr = removeDuplicates(wordsArr);
            // Restrict word size in the array
            restrictedList = restrictWordSize(wordsArr, minWordSize, maxWordSize);

            // Populate pairsList
            for (String word: restrictedList) {
                pairsList.add(new Pair(word, i));
            }
        }

        // Now pairsList contains all of the data.
        // Store pairsList data to pairs array (class field)
        pairs = new Pair[pairsList.size()];
        pairs = pairsList.toArray(pairs);

        // Sort the array pairs in alphabetical order.
        Arrays.sort(pairs);
    }

    public void print() {
        for (Pair pair : pairs)
            System.out.println(pair.getWord() + ": " + pair.getLineOccurrence());
    }

    // Return ArrayList of strings with restricted length, tha exist in the given array of strings
    public static ArrayList<String> restrictWordSize(String[] arr, int minWordSize, int maxWordSize) {
        ArrayList<String> arrayList = new ArrayList<>();

        for (String str : arr) {
            if (str.length() >= minWordSize) {
                arrayList.add(str.substring(0, Math.min(str.length(), maxWordSize)));
            }
        }

        return arrayList;
    }

    // Remove duplicates from array
    public static String[] removeDuplicates(String[] array) {
        HashSet<String> set = new HashSet<>(Arrays.asList(array));

        return set.toArray(new String[0]);
    }

}
