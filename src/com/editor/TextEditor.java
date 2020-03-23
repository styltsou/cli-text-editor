package com.editor;

import java.io.*;
import java.util.Scanner;

public class TextEditor {
    private String basePath;
    private String filePath;
    private DoublyLinkedList<Line> editorContent;
    private int maxLineLength;
    private int currentLine;
    private boolean isLineNumberVisible;

    public TextEditor(int maxLineLength) {
        filePath = "";
        this.editorContent = new DoublyLinkedList<>();
        this.maxLineLength = maxLineLength;
        this.currentLine = 1;
        this.isLineNumberVisible = true;
    }

    // TODO: Probably need to change the type of argument to a collection type
    public TextEditor(DoublyLinkedList<Line> editorContent, int maxLineLength) {
        this.editorContent = editorContent;
        this.maxLineLength = maxLineLength;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public DoublyLinkedList<Line> getEditorContent() {
        return editorContent;
    }

    public void setEditorContent(DoublyLinkedList<Line> editorContent) {
        this.editorContent = editorContent;
    }

    public int getCurrentLine() {
        return currentLine;
    }

    public void setCurrentLine(int currentLine) {
        this.currentLine = currentLine;
    }

    public boolean isLineNumberVisible() {
        return isLineNumberVisible;
    }

    public void setLineNumberVisible(boolean lineNumberVisible) {
        isLineNumberVisible = lineNumberVisible;
    }


    // ~ METHODS IMPLEMENTATION ~
    public void parseFile(File file) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String lineText;
            int lineNumber = 1;

            while ((lineText = br.readLine()) != null) {
                editorContent.append(new Line(lineNumber, lineText, maxLineLength));
                lineNumber++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToFirstLine() {
        if (editorContent.size() == 0) {
            System.out.println("There are no lines in the editor.");
            return;
        } else {
            currentLine = 1;
        }

        System.out.println("You are on the first line.");
    }

    public void goToLastLine() {
        if (editorContent.size() == 0) {
            System.out.println("The are no lines in the editor.");
            return;
        } else {
            currentLine = editorContent.size();
        }

        System.out.println("You are on the last line.");
    }

    public void goToPreviousLine() {
        if (editorContent.size() == 0) {
            System.out.println("There are no lines in the editor.");

        } else if (currentLine == 1) {
            System.out.println("You already are on the first line.");
        } else {
            currentLine--;
            System.out.println("Moved one line up.");
        }
    }

    public void goToNextLine() {
        if (editorContent.size() == 0) {
            System.out.println("There are no lines in the editor.");
        } else if (currentLine == editorContent.size()) {
            System.out.println("You already are on the last line.");
        } else {
            currentLine++;
            System.out.println("Moved one line down.");
        }
    }

    // TODO: Might need a new way of setting line number
    public void insertLineAfter(Scanner scanner) {
        System.out.println("Type in the text you want to insert:");
        String text = scanner.nextLine();


        if (editorContent.size() == 0) {
            editorContent.append(new Line(currentLine, text, maxLineLength));
            System.out.println("Line inserted.");
            return;
        } else {
            editorContent.insertAfter(editorContent.getNthNode(currentLine), new Line(currentLine + 1, text, maxLineLength));
            resetLineNumbersAfterInsert(currentLine + 1);
        }

        System.out.println("Line inserted");
    }

    public void insertLineBefore(Scanner scanner) {
        System.out.println("Type in the text you want to insert:");
        String text = scanner.nextLine();

        Line line = new Line(currentLine, text, maxLineLength);

        if (editorContent.size() == 0) {
            editorContent.append(line);
            System.out.println("Line inserted.");
            return;
        } else if (currentLine == 1) {
            editorContent.push(line);
        } else {
            editorContent.insertBefore(editorContent.getNthNode(currentLine), line);
        }

        resetLineNumbersAfterInsert(currentLine);

        System.out.println("Line inserted.");
    }

    public void deleteLine() {
        editorContent.remove(editorContent.getNthNode(currentLine));
        resetLineNumbersAfterRemove(currentLine);
        System.out.println("Line removed");
    }

    public void printAllLines() {
        if (editorContent.size() == 0) {
            System.out.println("The text editor is empty.");
            return;
        }

        for (int i = 1; i <= editorContent.size(); i++) {
            editorContent.getNthNode(i).data.printLine(isLineNumberVisible);
        }
    }

    public void toggleLineNumbers() {
        isLineNumberVisible = !isLineNumberVisible;
        if (isLineNumberVisible) System.out.println("Displaying line numbers in the editor.");
        else System.out.println("Removed line numbers from the editor.");
    }

    public void printLine() {
        if (editorContent.size() == 0) {
            System.out.println("The editor is empty");
            return;
        }

        editorContent.getNthNode(currentLine).data.printLine(isLineNumberVisible);
    }

    // Save the file to disk.
    public void saveFile(Scanner scanner) {
        File file;
        if (filePath.length() == 0) {
            System.out.println("Enter file name:");
            String filename = scanner.nextLine();

            filePath = basePath + filename;
        }
        // Create a new file
        file = new File(filePath);

        // Save file to the predefined directory.
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));

            for (int i = 1; i <= editorContent.size(); i++) {
                bw.write(editorContent.getNthNode(i).data.getText());
                bw.newLine();
            }

            bw.close();
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void printLineNumber() {
        System.out.println("Current line: " + currentLine);
    }

    public void printNumOfLinesAndChars() {
        int numOfCharacters = 0;
        if (editorContent.size() == 0) {
            System.out.println("Lines: " + editorContent.size());
            System.out.println("Number of characters: " + numOfCharacters);

            return;
        }

        for (int i = 1; i <= editorContent.size(); i++) {
            numOfCharacters += editorContent.getNthNode(i).data.getText().length();
        }

        System.out.println("Lines: " + editorContent.size());
        System.out.println("Number of characters: " + numOfCharacters);
    }

    public void createIdxFile() {

    }

    //TODO: Util functions to reset line numbers in the text editor after a line insertion or removal.
    // lineNumber represents the number of the line that was inserted or removed.
    public void resetLineNumbersAfterInsert(int lineNumber) {
        for (int i = lineNumber + 1; i <= editorContent.size(); i++) {
            editorContent.getNthNode(i).data.setLineNumber(editorContent.getNthNode(i).data.getLineNumber() + 1);
        }
    }

    public void resetLineNumbersAfterRemove(int lineNumber) {
        for (int i = lineNumber; i <= editorContent.size(); i++) {
            editorContent.getNthNode(i).data.setLineNumber(editorContent.getNthNode(i).data.getLineNumber() - 1);
        }
    }
}
