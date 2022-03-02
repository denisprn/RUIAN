package com.bp.RUIAN.services;

import com.bp.RUIAN.entities.*;
import com.bp.RUIAN.parsers.AddressConverter;
import com.bp.RUIAN.repositories.*;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import org.elasticsearch.client.RestHighLevelClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Service
public class EsService {
    //private final RestHighLevelClient esClient;
    private final AddressSearch addressSearch;
    private final AddressRepository addressRepository;
    private final CsvService csvService;

    @Autowired
    public EsService(/*RestHighLevelClient esClient*/AddressSearch addressSearch,
                     AddressRepository addressRepository,
                     CsvService csvService) {
        //this.esClient = esClient;
        this.addressSearch = addressSearch;
        this.addressRepository = addressRepository;
        this.csvService = csvService;
    }

    public List<String> search(String searchString) throws IOException {
        return addressSearch.search(searchString);
    }

    public Optional<Address> findAddressById(Integer id) {
        return addressRepository.findById(id);
    }

    public void updateData() throws FileNotFoundException, UnsupportedEncodingException {
        csvService.updateData();
    }
}
