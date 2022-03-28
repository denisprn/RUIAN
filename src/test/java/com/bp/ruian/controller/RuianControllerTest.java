package com.bp.ruian.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Tests endpoints
 * @author denisprn
 */
@SpringBootTest
@AutoConfigureMockMvc
class RuianControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("'/ruian/search' endpoint returns 5 items test")
    void findAddressesBySearchString5ItemsTest() throws Exception {
        mockMvc.perform(get("/ruian/search?query={query}", "Praha"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.*", Matchers.hasSize(5)));
    }

    @Test
    @DisplayName("'/ruian/search' endpoint returns 0 items test")
    void findAddressesBySearchString0ItemsTest() throws Exception {
        mockMvc.perform(get("/ruian/search?query={query}", ""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.*", Matchers.hasSize(0)));
    }

    @Test
    @DisplayName("'/ruian/address/{id}' endpoint with OK status test")
    void findAddressByIdStatusOkTest() throws Exception {

        mockMvc.perform(get("/ruian/address/{id}", 74979388))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.aMapWithSize(19)))
                .andExpect(jsonPath("$.id", Matchers.equalTo(74979388)));
    }

    @Test
    @DisplayName("'/ruian/address/{id}' endpoint with Not Found status test")
    void findAddressByIdStatusNotFoundTest() throws Exception {

        mockMvc.perform(get("/ruian/address/{id}", 1))
                .andExpect(status().isNotFound());
    }
}
