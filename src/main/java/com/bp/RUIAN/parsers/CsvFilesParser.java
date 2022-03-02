package com.bp.RUIAN.parsers;

import com.bp.RUIAN.entities.Address;
import com.bp.RUIAN.repositories.AddressRepository;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvFilesParser implements FilesParser, BulkDataUploader<Address> {
    @Value("${file.encoding-charset}")
    private String fileEncoding;

    @Value("${bulk.number-of-records}")
    private int numberOfRecords;

    private final AddressRepository addressRepository;

    @Autowired
    public CsvFilesParser(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public void upload(List<Address> addressList) {
        addressRepository.saveAll(addressList);
    }

    @Override
    public void parseFile(String csvFIlePath) {
        List<Address> addressList = new ArrayList<>(numberOfRecords);
        CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build();

        try (CSVReader reader = new CSVReaderBuilder(
                new FileReader(csvFIlePath, Charset.forName(fileEncoding)))
                .withCSVParser(csvParser)
                .withSkipLines(1)
                .build()) {
            String[] addressValues;
            int counter = 0;

            while (((addressValues = reader.readNext()) != null)) {
                Address address = new AddressConverter(addressValues).convert();
                addressList.add(address);

                if (++counter == numberOfRecords) {
                    upload(addressList);
                    addressList.clear();
                    counter = 0;
                }
            }

            if (counter != 0) {
                upload(addressList);
            }
        } catch (IOException | CsvException | ParseException exception) {
            throw new RuntimeException("Failed to parse CSV data: " + exception.getMessage());
        }
    }
}
