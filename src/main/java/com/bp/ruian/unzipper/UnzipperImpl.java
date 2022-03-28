package com.bp.ruian.unzipper;

import com.bp.ruian.utils.ExceptionMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Unzips .zip file
 * @author denisprn
 */
@Component
public class UnzipperImpl implements Unzipper {
    private static final Logger LOGGER = LoggerFactory.getLogger(UnzipperImpl.class);

    @Override
    public void unzip(String zipFilePath, String destDirPath) {
        byte[] buffer = new byte[1024];

        File destDir = new File(destDirPath);

        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry zipEntry = zis.getNextEntry();

            while (zipEntry != null) {
                File newFile = newFile(destDir, zipEntry);

                if (Boolean.TRUE.equals(zipEntry.isDirectory())) {
                    if (Boolean.FALSE.equals(newFile.isDirectory()) &&
                            Boolean.FALSE.equals(newFile.mkdirs())) {
                        throw new IOException(String.format("%s%s", ExceptionMessages.DIRECTORY_CREATION_ERROR, newFile));
                    }
                } else {
                    File parent = newFile.getParentFile();

                    if (Boolean.FALSE.equals(parent.isDirectory()) &&
                            Boolean.FALSE.equals(parent.mkdirs())) {
                        throw new IOException(String.format("%s%s", ExceptionMessages.DIRECTORY_CREATION_ERROR, parent));
                    }

                    writeFile(newFile, buffer, zis);
                }

                zipEntry = zis.getNextEntry();
            }

            zis.closeEntry();
        } catch (IOException exception) {
            LOGGER.error(exception.getMessage());
        }
    }

    @NonNull
    private File newFile(@NonNull File destinationDir,
                         @NonNull ZipEntry zipEntry) throws IOException {
        String zipEntryName = zipEntry.getName();
        File destFile = new File(destinationDir,zipEntryName);

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (Boolean.FALSE.equals(destFilePath.startsWith(
                String.format("%s%s", destDirPath, File.separator)))) {
            throw new IOException(
                    String.format("%s%s", ExceptionMessages.ENTRY_OUTSIDE_DIRECTORY_ERROR, zipEntryName));
        }

        return destFile;
    }

    @NonNull
    private void writeFile(@NonNull File newFile,
                           @NonNull byte[] buffer,
                           @NonNull ZipInputStream zis) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(newFile)) {
            int len;

            while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
        }
    }
}
