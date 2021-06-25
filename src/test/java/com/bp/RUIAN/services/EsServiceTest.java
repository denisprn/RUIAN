package com.bp.RUIAN.services;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EsServiceTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Should return search result of street and number")
    public void result1() throws Exception {
        String result = """
                [
                    "<b>Masarykovo náměstí 18/16</b><br>Adresa, Jihlava",
                    "<b>Masarykovo náměstí 67/14</b><br>Adresa, Jihlava",
                    "<b>Masarykovo náměstí 16/65A</b><br>Adresa, Jihlava"
                ]""";

        this.mockMvc.perform(get("/ruian/search?query={query}", "masarykovo naměstí 1"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().json(result));
    }

    @Test
    @DisplayName("Should return search result of street and numbers with '/'")
    public void result2() throws Exception {
        String result = """
                [
                    "<b>Masarykovo náměstí 16/65A</b><br>Adresa, Jihlava"
                ]""";

        this.mockMvc.perform(get("/ruian/search?query={query}", "masarykovo naměstí 1/6A"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().json(result));
    }

    @Test
    @DisplayName("Should return search result of street and numbers with '/'")
    public void result3() throws Exception {
        String result = """
                [
                    "<b>Masarykovo náměstí 63/56</b><br>Adresa, Jihlava",
                    "<b>Masarykovo náměstí 62/57</b><br>Adresa, Jihlava"
                ]""";

        this.mockMvc.perform(get("/ruian/search?query={query}", "masarykovo naměstí 6/5"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().json(result));
    }
}
