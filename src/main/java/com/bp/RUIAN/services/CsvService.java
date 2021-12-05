package com.bp.RUIAN.services;

import com.bp.RUIAN.parsers.CSVFilesParser;
import com.bp.RUIAN.utils.UnzipFile;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Service
public class CsvService {
    @Autowired
    private EsService esService;

    private @NotNull String getLastMonthsLastDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);

        int max = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, max);

        return sdf.format(calendar.getTime());
    }

    private String prepareUrl() {
        String lastMonthsLastDate = getLastMonthsLastDate();
        return String.format(
                "https://vdp.cuzk.cz/vymenny_format/csv/%s_OB_ADR_csv.zip", lastMonthsLastDate);
    }

    private void downloadArchiveWithCsvFiles() {
        try {
            String csvFileUrl = prepareUrl();
            InputStream inputStream = new URL(csvFileUrl).openStream();
            String destinationFilePath = "." + File.separator + "Addresses.zip";
            Files.copy(inputStream, Paths.get(destinationFilePath),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception exception) {
            throw new RuntimeException("Failed to download csv data: " + exception.getMessage());
        }
    }

    private void unzipArchiveWithCsvFiles() {
        String zipFilePath = "." + File.separator + "Addresses.zip";
        String destinationDirPath = "." + File.separator + "resources" + File.separator;

        new UnzipFile().unzip(zipFilePath, destinationDirPath);
    }

    private void parseAndUploadDataFromCsvFiles() {
        try {
            String csvDirectoryPath = "." + File.separator + "resources" + File.separator + "CSV" + File.separator;
            new CSVFilesParser(esService).walk(csvDirectoryPath);
        } catch (IOException exception) {
            throw new RuntimeException("Failed to upload csv data: " + exception.getMessage());
        }
    }

    public void updateData() {
        downloadArchiveWithCsvFiles();
        unzipArchiveWithCsvFiles();
        parseAndUploadDataFromCsvFiles();
    }
}
