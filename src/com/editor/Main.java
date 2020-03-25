package com.editor;

import java.io.File;
import java.io.IOException;

public class Main {

    // Return a file from given its base path and name.
    public static File findFile(String basePath, String filename) {
        return new File(basePath + filename);
    }

    public static void main(String[] args) {
        // Set editor's max line length
        int MAX_LINE_LENGTH = 80;

        // Set for indexing file parameters
        int MIN_WORD_SIZE = 1;
        int MAX_WORD_SIZE = 6;
        int BUFFER_SIZE = 50;

        TextEditor editor;

        // Set the path where the program looks for files.
        String basePath = System.getProperty("user.home") + "/Desktop/";

        // Change path's format to be compatible with Windows if needed.
        if (System.getProperty("os.name").toLowerCase().equals("windows")) {
            basePath = basePath.replace('/', '\\');
        }

        // Parse command line argument (filename) and open a new text editor.
        if (args.length > 0) {
            File file = findFile(basePath, args[0]);

            // Set up a new editor and set the file path for the file that is open in the editor.
            editor = new TextEditor(MAX_LINE_LENGTH, MIN_WORD_SIZE, MAX_WORD_SIZE, BUFFER_SIZE);
            editor.setFilePath(file.getPath());

            if (file.exists()) {
                editor.parseFile(file);
                System.out.println(args[0] + " is open in the editor.");
            } else {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println("File does not exist. A new empty file was created");
            }
        } else {
           editor = new TextEditor(MAX_LINE_LENGTH, MIN_WORD_SIZE, MAX_WORD_SIZE, BUFFER_SIZE);
           editor.setBasePath(basePath);

           System.out.println("A blank text editor has opened.");
        }

        CommandListener.listen(editor);
    }
}
