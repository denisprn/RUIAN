package com.bp.RUIAN.parsers;

import com.bp.RUIAN.entities.Address;
import com.bp.RUIAN.services.EsService;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import java.io.*;
import java.nio.charset.Charset;
import java.text.ParseException;

public class CSVFilesParser extends FilesParser {
    public CSVFilesParser(EsService esService) {
        super(esService);
        this.fileExtension = ".csv";
    }

    @Override
    public void parseFile(String filePath) {
        CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build();

        try (CSVReader reader = new CSVReaderBuilder(
                new FileReader(filePath, Charset.forName("windows-1250")))
                .withCSVParser(csvParser)
                .withSkipLines(1)
                .build())
        {
            String[] line;

            while ((line = reader.readNext()) != null) {
                Address address = new AddressParser(line).parse();
                esService.saveAddress(address);
            }
        } catch (IOException | CsvException | ParseException e) {
            e.printStackTrace();
        }
    }
}
