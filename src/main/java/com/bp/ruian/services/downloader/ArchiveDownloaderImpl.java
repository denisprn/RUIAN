package com.bp.ruian.services.downloader;

import com.bp.ruian.utils.LoggerMessages;
import com.bp.ruian.utils.ResourcePaths;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author denisprn
 */
@Component
public class ArchiveDownloaderImpl implements ArchiveDownloader{

    private static final Logger LOGGER = LoggerFactory.getLogger(ArchiveDownloaderImpl.class);

    private @NotNull String getLastMonthsLastDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);

        int max = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, max);

        final String lastMonthLastDay = sdf.format(calendar.getTime());

        final String formattedMessage =
                String.format("%s %s", LoggerMessages.LAST_MONTHS_LAST_DAY_INFO, lastMonthLastDay);

        LOGGER.info(formattedMessage);

        return lastMonthLastDay;
    }

    @Override
    public void downloadArchive() {
        final String lastMonthsLastDate = getLastMonthsLastDate();
        final String archiveUrl =
                String.format("https://vdp.cuzk.cz/vymenny_format/csv/%s_OB_ADR_csv.zip", lastMonthsLastDate);

        try (InputStream in = new URL(archiveUrl).openStream()) {
            LOGGER.info(LoggerMessages.ARCHIVE_DOWNLOADING_STARTED);

            Files.copy(in, Paths.get(ResourcePaths.ARCHIVE_FILE_LOCATION), StandardCopyOption.REPLACE_EXISTING);

            LOGGER.info(LoggerMessages.ARCHIVE_DOWNLOADED_INFO);
        } catch (IOException exception) {
            final String loggerMessage =
                    String.format("%s. %s", LoggerMessages.ARCHIVE_DOWNLOADING_ERROR, exception.getMessage());

            LOGGER.error(loggerMessage);
        }
    }
}
