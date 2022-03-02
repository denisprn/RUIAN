package com.bp.RUIAN.parsers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;


public interface FilesParser {
    default void walkDirectoriesAndParseFiles(final String directoryPath) throws FileNotFoundException, UnsupportedEncodingException {
        File root = new File(directoryPath);
        File[] arrayOfFileNames = root.listFiles();

        if (arrayOfFileNames == null) {
            return;
        }

        for (File file : arrayOfFileNames) {
            if (file.isDirectory()) {
                walkDirectoriesAndParseFiles(file.getAbsolutePath());
            } else if (file.getName().endsWith("csv")) {
                parseFile(file.getAbsolutePath());
            }
        }
    }

    void parseFile(String filePath) throws FileNotFoundException, UnsupportedEncodingException;
}
