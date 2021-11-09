package com.bp.RUIAN.parsers;


import com.bp.RUIAN.services.EsService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public abstract class FilesParser {
    protected String fileExtension;
    protected final EsService esService;

    public FilesParser(EsService esService) {
        this.esService = esService;
    }

    public void walk(String directoryPath) throws FileNotFoundException, UnsupportedEncodingException {
        File root = new File(directoryPath);
        File[] list = root.listFiles();

        if (list == null) return;

        for (File f : list) {
            if (f.isDirectory()) {
                walk(f.getAbsolutePath());
            }
            else if (f.getName().endsWith(fileExtension)) {
                parseFile(f.getAbsolutePath());
            }
        }
    }

    protected abstract void parseFile(String filePath) throws FileNotFoundException, UnsupportedEncodingException;
}
