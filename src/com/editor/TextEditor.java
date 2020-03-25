package com.editor;

import java.io.*;
import java.util.Scanner;
import java.nio.ByteBuffer;

import static java.nio.charset.StandardCharsets.US_ASCII;

public class TextEditor {
    private String basePath;
    private String filePath;
    private DoublyLinkedList<Line> editorContent;
    private int maxLineLength;
    private int currentLine;
    private boolean isLineNumberVisible;
    private int minWordSize;
    private int maxWordSize;
    private int bufferSize;

    // Constructor
    public TextEditor(int maxLineLength, int minWordSize, int maxWordSize, int bufferSize) {
        basePath ="";
        filePath = "";
        this.editorContent = new DoublyLinkedList<>();
        this.maxLineLength = maxLineLength;
        this.currentLine = 1;
        this.isLineNumberVisible = true;
        this.minWordSize = minWordSize;
        this.maxWordSize = maxWordSize;
        this.bufferSize = bufferSize;
    }

    public TextEditor(DoublyLinkedList<Line> editorContent, int maxLineLength) {
        this.editorContent = editorContent;
        this.maxLineLength = maxLineLength;
    }

    // SETTERS & GETTERS

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

    public int getMaxLineLength() {
        return maxLineLength;
    }

    public void setMaxLineLength(int maxLineLength) {
        this.maxLineLength = maxLineLength;
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

    public int getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }


    // ~ METHODS ~

    // Parse a text file and insert it's data into the 'editorContent' array list.
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
            System.out.println("There are no lines in the editor. Type in 'a' or 't' to insert one.");

        } else if (currentLine == 1) {
            System.out.println("You already are on the first line.");
        } else {
            currentLine--;
            System.out.println("Moved one line up.");
        }
    }

    public void goToNextLine() {
        if (editorContent.size() == 0) {
            System.out.println("There are no lines in the editor. Type in 'a' or 't' to insert one.");
        } else if (currentLine == editorContent.size()) {
            System.out.println("You already are on the last line.");
        } else {
            currentLine++;
            System.out.println("Moved one line down.");
        }
    }

    // Insert a line after the current line.
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

    // Insert a line before the current line.
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

    // Delete current line from the editor.
    public void deleteLine() {
        editorContent.remove(editorContent.getNthNode(currentLine));
        resetLineNumbersAfterRemove(currentLine);

        if (currentLine == editorContent.size() + 1) {
            currentLine--;
        }

        System.out.println("Line removed");
    }

    // Print all the lines in the editor.
    public void printAllLines() {
        if (editorContent.size() == 0) {
            System.out.println("The text editor is empty.");
            return;
        }

        for (int i = 1; i <= editorContent.size(); i++) {
            editorContent.getNthNode(i).data.printLine(isLineNumberVisible);
        }
    }

    // Toggle weather line numbers are displayed
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
        // The name of the file to save on disk
        String filename;

        if (filePath.length() == 0) {
            while (true) {
                System.out.println("Enter file name:");
                filename = scanner.nextLine();

                filePath = basePath + filename + ".txt";

                File file = new File(filePath);

                if (file.exists()) {
                    System.out.println("A file with that name already exists.");
                } else {
                    break;
                }
            };
        }

        // Create a new file
        File file = new File(filePath);

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

    // Print the number of the current line.
    public void printLineNumber() {
        System.out.println("Current line: " + currentLine);
    }

    // Print the number of lines in the text file and the total number of characters.
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

    // Create a new index file and print the number of pages.
    public void createIdxFile() {
        RandomAccessFile idxFile = null;

        // If there's no text file saved on disk, display an appropriate message.
        if (filePath.length() == 0) {
            System.out.println("You need to save the file on disk in order to create an indexing file.");
            return;
        }

        // Create a new indexing file named '[text_file_name].txt.ndx'
        try {
            idxFile = new RandomAccessFile(filePath + ".ndx", "rw");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create a new indexing array (idxArray)
        IndexingArray array = new IndexingArray(minWordSize, maxWordSize);
        array.populate(editorContent);
        Pair[] idxArray = array.getPairs();

        // Create a new Byte Buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(bufferSize);
        // Number of entries that a buffer can hold.
        // every entry's size is maxWordSize + 4 bytes
        int numOfMaxEntries = Math.floorDiv(bufferSize, maxWordSize + 4);
        // The number of the entries inside the buffer.
        int entryNumber = 0;

        // Insert indexing array's data into the buffer
        for (Pair pair : idxArray) {
            // if byte buffer is not full
             // add pair inside
            //else dump buffer's data into the file and then clear it.
            if (entryNumber <= numOfMaxEntries) {
                insertPairToBuffer(byteBuffer, pair);
                entryNumber++;
            } else {
                writeBufferToFile(byteBuffer, idxFile);
                System.out.println("Made it here");
                byteBuffer.clear();
                System.out.println("Should be clear");
            }
        }

        // Write remaining data from buffer to index file.
        writeBufferToFile(byteBuffer, idxFile);
        byteBuffer.clear();

        // Add 0 in the end of the file.
        try {
            idxFile.seek(idxFile.length());
            idxFile.write("0".getBytes(US_ASCII));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int numOfPages = 0;
        // The actual size of the data in bytes is 4 bytes less than the files size due to the 0 integer in the end of the last page.
        try {
            numOfPages = (int) Math.ceil((double) (idxFile.length() - 4) / bufferSize);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Indexing file created successfully");
        System.out.println("Number of pages: " + numOfPages);
    };

//     Print index file's data in the terminal.
    public void printIdxFile() {
        if (!doesIdxFileExists()) {
            System.out.println("Index file doesn't exists.\nType in 'c' to create one");
            return;
        }


    }

    // Print the results of a word search.
    // Argument 'isLinearSearch' determines the searching algorithm to be applied.
    public void printSearchResults(Scanner scanner, boolean isLinearSearch) {
        if (!doesIdxFileExists()) {
            System.out.println("You must create an indexing file in order to search for a word.\nType in 'c' to create one");
            return;
        }

        System.out.println("Type a word for search");
        String word = scanner.nextLine();

        // Get search data depending on the search algorithm.
        SearchData searchData;

        if (isLinearSearch) {
            searchData = linearSearch(word);
        } else {
            searchData = binarySearch(word);
        }

        System.out.println("'" + word + "' is on lines:");
        for (int lineOccurrence : searchData.getLineOccurrences()) {
            System.out.print(lineOccurrence);
        }

        System.out.println("\nDisk accesses:" + searchData.getDiskAccesses());
    }

    // Search for a word in the file using linear search.
    // Returns the line numbers where the word occurs and the number of disk accesses.
    public SearchData linearSearch(String word) {
        return new SearchData();
    };

    // Search for a word in the file using binary search.
    // Returns the line numbers where the word occurs and the number of disk accesses.
    public SearchData binarySearch(String word) {
        return new SearchData();
    }

    // UTILITY METHODS

    // Reset the line numbers after line insertion or removal respectively.
    // LineNumber represents the number of the line that was inserted or removed.
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

    // Check if an index file exists on disk
    public boolean doesIdxFileExists() {
        // If there is no text file saved on disk, the corresponding index file doesn't exist.
        if (filePath.length() == 0) {
            return false;
        }

        File file = new File(filePath + ".ndx");
        return file.exists();
    }

    // Insert a Pair object inside a ByteBuffer
    public void insertPairToBuffer(ByteBuffer byteBuffer, Pair pair) {
        // Inset word into the buffer;
        byteBuffer.put(pair.getWord().getBytes(US_ASCII));

        // If there is remaining space, fill it with space characters
        if (pair.getWord().length() < maxWordSize) {
            for (int i = 0; i < maxWordSize - pair.getWord().length(); i++) {
                byteBuffer.put(" ".getBytes(US_ASCII));
            }
        }
        // Insert line number into the buffer
        byteBuffer.putInt(pair.getLineOccurrence());
    }

    public void writeBufferToFile(ByteBuffer byteBuffer, RandomAccessFile idxFile) {
        // Create byte array
        byte[] byteArray = byteBuffer.array();
        // Write data to file
        try {
            // write byte array's data to lines.
            for (int i = 0; i < byteArray.length; i++) {
                idxFile.seek(idxFile.length());
                idxFile.write(byteArray[i]);

                // Separate entries with a new line
                // entrySize - 1 = maxWordSize + 3
//                if ((i % (maxWordSize + 3)) == 0) {
//                    idxFile.writeBytes(System.getProperty("line.separator"));
//                }
            }
            // Write a zero in the end of the file to indicate the last line of data.
            idxFile.seek(idxFile.length());
//                    idxFile.write("0".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
