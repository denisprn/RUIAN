package com.bp.ruian.finder;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.lang.NonNull;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests {@link AddressFinder} functionality
 * @author denisprn
 */
@SpringBootTest
class AddressFinderTest {

    @Autowired
    private AddressFinderImpl addressFinder;

    @Test
    @DisplayName("Address searching test")
    void searchTest() {
        Map <String, Integer> testData = prepareTestData();

        testData.forEach((key, value) -> {
            assertThat(addressFinder.find(key).get(0).id()).isEqualTo(value);
        });
    }

    @NonNull
    private Map<String, Integer> prepareTestData() {
        Map<String, Integer> addresses = new HashMap<>();

        addresses.put("Jankovcova 49/1037 Praha", 22306196);
        addresses.put("jank 1037 praa", 22306196);
        addresses.put("Valdštejnské namestí 17/4, 118 00 PRAHA 011", 21694168);
        addresses.put("valds nam 17 4 prah", 21694168);
        addresses.put("Barrandovká 1236/56b, 15200", 74979388);
        addresses.put("706/3 19015 Praha", 75156423);
        addresses.put("Třeboutice 13 Křešice", 25023306);
        addresses.put("Masaryka 150 praha", 21740763);
        addresses.put("Vysokk nad Labem 294, PSČ 50331", 26920964);
        addresses.put("Dolní Přím 101", 81909802);
        addresses.put("Rtyně nad Bílinou, 6, 41501", 20207085);
        addresses.put("Rtyne nad 41501., 60 ", 20207565);
        addresses.put("Jizerska 2936, Ústí ", 17879965);
        addresses.put("Císare Zikmunda 1551, Brod", 79384650);
        addresses.put("ceskolipska 384/19, 19000, Praha", 22516875);
        addresses.put("svermova 198 Liberec XX-Ostašov 46010 Liberec", 78968208);
        addresses.put("Pod Maxovkou 946, Libušín ", 78973716);
        addresses.put("Zahradní 427, 43145 Březno", 77852401);

        return addresses;
    }
}
