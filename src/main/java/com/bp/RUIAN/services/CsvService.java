package com.bp.RUIAN.services;

import com.bp.RUIAN.parsers.CsvFilesParser;
import com.bp.RUIAN.utils.UnzipFile;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${archive.dest-dir}")
    private String destinationDirectoryPath;

    @Value("${archive.location-dir}")
    private String archivePath; //= String.format(".%1$saddresses%1$sAddresses.zip", File.separator);

/*    @Value("${csv-file.dir}")
    private String csvDirPath;*/

    @Value("${archive.url}")
    private String archiveUrl;

    private final CsvFilesParser csvFilesParser;

    @Autowired
    public CsvService(CsvFilesParser csvFilesParser) {
        this.csvFilesParser = csvFilesParser;
    }

    private @NotNull String getLastMonthsLastDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);

        int max = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, max);

        return sdf.format(calendar.getTime());
    }

    public void downloadArchive() {
        final String lastMonthsLastDate = getLastMonthsLastDate();
        final String csvFileUrl = String.format(archiveUrl, lastMonthsLastDate);

        try (InputStream in = new URL(csvFileUrl).openStream()) {
            Files.copy(in, Paths.get(archivePath), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception exception) {
            throw new RuntimeException("Failed to download csv data: " + exception.getMessage());
        }
    }

    public void unzipArchive() {
        archivePath = String.format(archivePath, File.separator);
        destinationDirectoryPath = String.format(destinationDirectoryPath, File.separator);
        //final String destinationDirectoryPath = String.format(".%1$saddresses%1$s", File.separator);
        new UnzipFile().unzip(archivePath, destinationDirectoryPath);
    }

    public void parseData() throws FileNotFoundException, UnsupportedEncodingException {
        //csvDestinationDirPath = String.format(csvDirPath, File.separator);
        //final String csvDirPath = "";
        //csvFilesParser.walkDirectoriesAndParseFiles(csvDirPath);
    }

    public void updateData() throws FileNotFoundException, UnsupportedEncodingException {
        downloadArchive();
        unzipArchive();
        parseData();
    }
}
