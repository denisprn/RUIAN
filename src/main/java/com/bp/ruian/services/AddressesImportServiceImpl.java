package com.bp.ruian.services;

import com.bp.ruian.services.converter.AddressConverterImpl;
import com.bp.ruian.services.downloader.ArchiveDownloaderImpl;
import com.bp.ruian.record.Address;
import com.bp.ruian.services.unzipper.UnzipperImpl;
import com.bp.ruian.services.uploader.BulkAddressDataUploaderImpl;
import com.bp.ruian.utils.FileExtensions;
import com.bp.ruian.utils.LoggerMessages;
import com.bp.ruian.utils.ResourcePaths;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link Address} import service
 * @author denisprn
 */
@Service
public class AddressesImportServiceImpl implements AddressesImportService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddressesImportServiceImpl.class);

    private final ArchiveDownloaderImpl archiveDownloader;

    private final UnzipperImpl unzipper;

    private final BulkAddressDataUploaderImpl bulkDataUploader;

    @Value("${file.encoding-charset}")
    private String fileEncoding;

    @Value("${bulk.number-of-records}")
    private int numberOfRecordsToUpload;

    @Autowired
    public AddressesImportServiceImpl(ArchiveDownloaderImpl archiveDownloader,
                                      UnzipperImpl unzipper,
                                      BulkAddressDataUploaderImpl bulkDataUploader) {
        this.archiveDownloader = archiveDownloader;
        this.unzipper = unzipper;
        this.bulkDataUploader = bulkDataUploader;
    }

    @Override
    public void importAddressesFromRuianToEs() {
        archiveDownloader.downloadArchive();
        unzipper.unzip(ResourcePaths.ARCHIVE_FILE_LOCATION, ResourcePaths.ADDRESSES_DIRECTORY);
        walkDirectories(ResourcePaths.ADDRESSES_DIRECTORY);
    }

    private void walkDirectories(final String directoryPath) {
        File root = new File(directoryPath);
        File[] arrayOfFileNames = root.listFiles();

        if (arrayOfFileNames == null) {
            return;
        }

        for (File file : arrayOfFileNames) {
            if (file.isDirectory()) {
                walkDirectories(file.getAbsolutePath());
            } else if (file.getName().endsWith(FileExtensions.CSV)) {
                parseAndImportData(file.getAbsolutePath());
            }
        }
    }

    private void parseAndImportData(String csvFilePath) {
        List<Address> addressList = new ArrayList<>(numberOfRecordsToUpload);
        CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build();

        try (CSVReader reader = new CSVReaderBuilder(
                new FileReader(csvFilePath, Charset.forName(fileEncoding)))
                .withCSVParser(csvParser)
                .withSkipLines(1)
                .build()) {
            String[] addressValues;
            int counter = 0;

            while (((addressValues = reader.readNext()) != null)) {
                Address address = new AddressConverterImpl().convertFromArrayValues(addressValues);
                addressList.add(address);

                if (++counter == numberOfRecordsToUpload) {
                    bulkDataUploader.bulkUpload(addressList);
                    addressList.clear();
                    counter = 0;
                }
            }

            if (counter != 0) {
                bulkDataUploader.bulkUpload(addressList);
            }
        } catch (IOException | CsvException exception) {
            LOGGER.error(String.format("%s. %s", LoggerMessages.CSV_PARSE_ERROR, exception.getMessage()));
        }
    }
}
