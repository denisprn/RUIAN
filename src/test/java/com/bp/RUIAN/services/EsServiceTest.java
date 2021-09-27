package com.bp.RUIAN.services;

import com.bp.RUIAN.entities.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EsServiceTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Should return search result of full given address")
    public void resultOfFullGivenAddress() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        List<Unit> listExpected = new ArrayList<>();
        Obec obec = Obec.create();
        Ulice ulice = Ulice.create();
        AdresniMisto adresniMisto = AdresniMisto.create();
        Unit unitExpected = new Unit(adresniMisto, ulice, obec);
        listExpected.add(unitExpected);

        MvcResult result = this.mockMvc.perform(get("/ruian/search?query={query}",
                        "jihlava masarykovo nam 65 16"))
                .andDo(print()).andExpect(status().isOk()).andReturn();

        String actualJson = result.getResponse().getContentAsString();
        String expectedJson = mapper.writeValueAsString(listExpected);

        Assertions.assertEquals(expectedJson, actualJson);
    }

    @Test
    @DisplayName("Should return search result of street and address")
    public void resultOfGivenStreetAndAddress() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        List<Unit> listExpected = new ArrayList<>();
        Obec obec = Obec.create();
        Ulice ulice = Ulice.create();
        AdresniMisto adresniMisto = AdresniMisto.create();
        Unit unitExpected = new Unit(adresniMisto, ulice, obec);
        listExpected.add(unitExpected);

        MvcResult result = this.mockMvc.perform(get("/ruian/search?query={query}",
                        "masarykovo nam 65 16"))
                .andDo(print()).andExpect(status().isOk()).andReturn();

        String actualJson = result.getResponse().getContentAsString();
        String expectedJson = mapper.writeValueAsString(listExpected);

        Assertions.assertEquals(expectedJson, actualJson);
    }

    @Test
    @DisplayName("Should return search result of given municipality and street")
    public void resultOfGivenMunicipalityAndStreet() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        List<Unit> listExpected = new ArrayList<>();
        Obec obec = Obec.create();
        Ulice ulice = Ulice.create();
        Unit unitExpected = new Unit(null, ulice, obec);
        listExpected.add(unitExpected);

        MvcResult result = this.mockMvc.perform(get("/ruian/search?query={query}",
                        "jihlava masarykovo nam"))
                .andDo(print()).andExpect(status().isOk()).andReturn();

        String actualJson = result.getResponse().getContentAsString();
        String expectedJson = mapper.writeValueAsString(listExpected);

        Assertions.assertEquals(expectedJson, actualJson);
    }

    @Test
    @DisplayName("Should return search result of given municipality")
    public void resultOfGivenMunicipality() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        List<Unit> listExpected = new ArrayList<>();
        Obec obec = Obec.create();
        Unit unitExpected = new Unit(null, null, obec);
        listExpected.add(unitExpected);

        MvcResult result = this.mockMvc.perform(get("/ruian/search?query={query}",
                        "jihlava"))
                .andDo(print()).andExpect(status().isOk()).andReturn();

        String actualJson = result.getResponse().getContentAsString();
        String expectedJson = mapper.writeValueAsString(listExpected);

        Assertions.assertEquals(expectedJson, actualJson);
    }

    @Test
    @DisplayName("Should return search result of given street")
    public void resultOfGivenStreet() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        List<Unit> listExpected = new ArrayList<>();
        Obec obec = Obec.create();
        Ulice ulice = Ulice.create();
        Unit unitExpected = new Unit(null, ulice, obec);
        listExpected.add(unitExpected);

        MvcResult result = this.mockMvc.perform(get("/ruian/search?query={query}",
                        "masarykovo nam"))
                .andDo(print()).andExpect(status().isOk()).andReturn();

        String actualJson = result.getResponse().getContentAsString();
        String expectedJson = mapper.writeValueAsString(listExpected);

        Assertions.assertEquals(expectedJson, actualJson);
    }

    @Test
    @DisplayName("Should return search result of given municipality and address")
    public void resultOfGivenMunicipalityAndAddress() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        List<Unit> listExpected = new ArrayList<>();
        AdresniMisto adresniMisto = AdresniMisto.create();
        Obec obec = Obec.create();
        Ulice ulice = Ulice.create();
        Unit unitExpected = new Unit(adresniMisto, ulice, obec);
        listExpected.add(unitExpected);

        MvcResult result = this.mockMvc.perform(get("/ruian/search?query={query}",
                        "jihlava 65 16"))
                .andDo(print()).andExpect(status().isOk()).andReturn();

        String actualJson = result.getResponse().getContentAsString();
        String expectedJson = mapper.writeValueAsString(listExpected);

        Assertions.assertEquals(expectedJson, actualJson);
    }
}
