package com.bp.RUIAN.parsers;

import com.bp.RUIAN.entities.Address;
import com.bp.RUIAN.services.EsService;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.Charset;
import java.text.ParseException;

@Component
public class CsvFilesParser extends FilesParser {
    @Autowired
    private EsService esService;

    public CsvFilesParser() {
        super(".csv");
    }

    @Override
    public void parseFile(String filePath) {
        CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build();

        try (CSVReader reader = new CSVReaderBuilder(
                new FileReader(filePath, Charset.forName(fileEncoding)))
                .withCSVParser(csvParser)
                .withSkipLines(1)
                .build())
        {
            String[] lineValues;

            while ((lineValues = reader.readNext()) != null) {
                Address address = new AddressConverter(lineValues).convert();
                esService.saveAddress(address);
            }
        } catch (IOException | CsvException | ParseException exception) {
            throw new RuntimeException("Failed to parse CSV data: " + exception.getMessage());
        }
    }
}
