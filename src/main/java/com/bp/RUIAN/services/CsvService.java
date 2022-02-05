package com.bp.RUIAN.services;

import com.bp.RUIAN.parsers.CsvFilesParser;
import com.bp.RUIAN.utils.UnzipFile;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Service
public class CsvService {
    private final String filePath = String.format(".%sAddresses.zip", File.separator);

    public void updateData() {
        downloadArchiveWithCsvFiles();
        unzipArchiveWithCsvFiles();
        parseAndUploadDataFromCsvFiles();
    }

    private @NotNull String getLastMonthsLastDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);

        int max = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, max);

        return sdf.format(calendar.getTime());
    }

    private String prepareUrl() {
        final String lastMonthsLastDate = getLastMonthsLastDate();

        return String.format(
                "https://vdp.cuzk.cz/vymenny_format/csv/%s_OB_ADR_csv.zip", lastMonthsLastDate);
    }

    private void downloadArchiveWithCsvFiles() {
        final String csvFileUrl = prepareUrl();

        try (InputStream in = new URL(csvFileUrl).openStream()) {
            Files.copy(in, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception exception) {
            throw new RuntimeException("Failed to download csv data: " + exception.getMessage());
        }
    }

    private void unzipArchiveWithCsvFiles() {
        final String destinationDirPath = String.format(".%1$sresources%1$s", File.separator);
        new UnzipFile().unzip(filePath, destinationDirPath);
    }

    private void parseAndUploadDataFromCsvFiles() {
        try {
            final String csvDirectoryPath = String.format(".%1$sresources%1$sCSV%1$s", File.separator);
            new CsvFilesParser().walk(csvDirectoryPath);
        } catch (IOException exception) {
            throw new RuntimeException("Failed to upload csv data: " + exception.getMessage());
        }
    }
}
