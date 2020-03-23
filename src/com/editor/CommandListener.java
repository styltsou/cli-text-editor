package com.editor;

import java.util.Scanner;

public class CommandListener {
    // Method that listens for command input.
    public static void listen(TextEditor editor) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            //TODO: Might need to move the scanner declaration outside the loop.
            String input = scanner.nextLine();
            switch (input) {
                case "^":
                    editor.goToFirstLine();
                    break;
                case "$":
                    editor.goToLastLine();
                    break;
                case "-":
                    editor.goToPreviousLine();
                    break;
                case "+":
                    editor.goToNextLine();
                    break;
                case "a":
                    editor.insertLineAfter(scanner);
                    break;
                case "t":
                    editor.insertLineBefore(scanner);
                    break;
                case "d":
                    editor.deleteLine();
                    break;
                case "l":
                    editor.printAllLines();
                    break;
                case "n":
                    editor.toggleLineNumbers();
                    break;
                case "p":
                    editor.printLine();
                    break;
                case "q":
                    System.out.println("Editor exited without saving.");
                    System.exit(0);
                    break;
                case "w":
                    editor.saveFile(scanner);
                    System.out.println("File saved to disk.");
                    break;
                case "x":
                    editor.saveFile(scanner);
                    System.out.println("Editor exited after saving the file to disk.");
                    System.exit(0);
                    break;
                case "=":
                    editor.printLineNumber();
                    break;
                case "#":
                    editor.printNumOfLinesAndChars();
                    break;
                case "c":
                    editor.createIdxFile();
                    break;
                case "v":
                    IndexingArray idxArr = new IndexingArray(1, 10);
                    idxArr.populate(editor.getEditorContent());

                    for (int i = 0; i < idxArr.getPairs().length; i++) {
                        System.out.println(idxArr.getPairs()[i].getWord() + " : " + idxArr.getPairs()[i].getLineOccurrence());
                    }
                    break;
                case "clear":
                    // Clear console's output
                    System.out.print("\033[H\033[2J");
                    break;
                default:
                    System.out.println("Invalid command!");
            }
        }
    }
}
