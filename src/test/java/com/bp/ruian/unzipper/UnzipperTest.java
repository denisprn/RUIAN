package com.bp.ruian.unzipper;

import com.bp.ruian.services.unzipper.Unzipper;
import com.bp.ruian.services.unzipper.UnzipperImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests {@link UnzipperImpl} functionality
 * @author denisprn
 */
@ContextConfiguration(classes = {
        UnzipperImpl.class
})
@SpringBootTest
class UnzipperTest {

    @Autowired
    private Unzipper unzipper;

    @Test
    @DisplayName("Test archive unzipping")
    void unzip() {
        final String addressesDirectoryPath = "./src/test/resources/addresses";
        final String zipFilePath = "./src/test/resources/zip/Addresses.zip";

        Path vresovaCsvFilePath = Paths.get("./src/test/resources/addresses/CSV/vresova.csv");
        Path dobrohostCsvFilePath = Paths.get("./src/test/resources/addresses/CSV/dobrohost.csv");

        unzipper.unzip(zipFilePath, addressesDirectoryPath);

        assertThat(Files.exists(vresovaCsvFilePath)).isTrue();
        assertThat(Files.exists(dobrohostCsvFilePath)).isTrue();
    }
}
