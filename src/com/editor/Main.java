package com.editor;

import java.io.File;

public class Main {

    // Return a file from given its base path and name.
    public static File findFile(String basePath, String filename) {
        return new File(basePath + filename);
    }

    public static void main(String[] args) {
        int MAX_LINE_LENGTH = 80;
        TextEditor editor;

        // Set the path where the program looks for files.
        String desktopPath = System.getProperty("user.home") + "/Desktop/";

        // Change path's format to be compatible with Windows if needed.
        if (System.getProperty("os.name").toLowerCase().equals("windows")) {
            desktopPath = desktopPath.replace('/', '\\');
        }

        // Parse command line argument (filename) and set up a new text editor.
        if (args.length > 0) {
            File file = findFile(desktopPath,args[0]);

            if (file.exists()) {
                editor = new TextEditor(MAX_LINE_LENGTH);
                editor.setFilePath(file.getPath());
                editor.parseFile(file);

                System.out.println(args[0] + " is open in the editor.");
            } else {
                editor = new TextEditor(MAX_LINE_LENGTH);
                editor.setBasePath(desktopPath);

                System.out.println("File does not exist. A blank text editor has opened.");
            }
        } else {
           editor = new TextEditor(MAX_LINE_LENGTH);
           editor.setBasePath(desktopPath);

            System.out.println("A blank text editor has opened.");
        }

        CommandListener.listen(editor);
    }
}
