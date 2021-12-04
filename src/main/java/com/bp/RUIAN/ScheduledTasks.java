package com.bp.RUIAN;

import com.bp.RUIAN.services.CsvService;
import com.bp.RUIAN.utils.UnzipFile;
import org.jetbrains.annotations.NotNull;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Component
public class ScheduledTasks {
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
            exception.printStackTrace();
        }
    }

    private void unzipArchiveWithCsvFiles() {
        String zipFilePath = "." + File.separator + "Addresses.zip";
        String destinationDirPath = "." + File.separator + "resources" + File.separator;

        new UnzipFile().unzip(zipFilePath, destinationDirPath);
    }

    private void uploadDataFromCsvFiles() {
        new CsvService().upload();
    }

    @Scheduled(cron = "0 0 1 1 * ?", zone = "Europe/Prague") //At 01:00:00am, on the 1st day, every month
    public void updateData() {
        try {
            downloadArchiveWithCsvFiles();
            unzipArchiveWithCsvFiles();
            uploadDataFromCsvFiles();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
