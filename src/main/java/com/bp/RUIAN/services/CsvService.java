package com.bp.RUIAN.services;

import com.bp.RUIAN.parsers.CSVFilesParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class CsvService {
    @Autowired
    private EsService esService;

    public void upload() {
        try {
            String csvDirectoryPath = "." + File.separator + "resources" + File.separator + "CSV" + File.separator;
            new CSVFilesParser(esService).walk(csvDirectoryPath);
        } catch (IOException exception) {
            throw new RuntimeException("Failed to upload csv data: " + exception.getMessage());
        }
    }
}
