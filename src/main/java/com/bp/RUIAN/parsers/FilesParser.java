package com.bp.RUIAN.parsers;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public interface FilesParser {
    default void walk(String directoryPath, String fileExtension) throws FileNotFoundException, UnsupportedEncodingException {
        File root = new File(directoryPath);
        File[] list = root.listFiles();

        if (list == null) return;

        for (File f : list) {
            if (f.isDirectory()) {
                walk(f.getAbsolutePath(), fileExtension);
            }
            else if (f.getName().endsWith(fileExtension)) {
                parseFile(f.getAbsolutePath());
            }
        }
    }

    void parseFile(String filePath) throws FileNotFoundException, UnsupportedEncodingException;
}
