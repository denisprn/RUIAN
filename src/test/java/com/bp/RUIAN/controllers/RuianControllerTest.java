package com.bp.RUIAN.controllers;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class RuianControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private RuianController ruianController;

	@Test
	@DisplayName("Should assert that controller is not null")
	public void contextLoads() {
		Assertions.assertNotNull(ruianController);
	}

	@Test
	@DisplayName("Should return search result of given address")
	public void resultOfGivenAddress() {
		String[] testQueries = new String[] {
				"Jank 1037 Praha",
				"brazec 65 36471",
				"holes 1587 8 d 17000",
				"praha jank 8/1587d 17000",
				"praha jank 1587d 8 17000",
				"praha 1587/ 8 17000",
				"javorna 1 brazec"
		};

		String[] expectedResults = new String[] {
				"Jankovcova 49/1037, 17000 Holešovice, Praha",
				"č.p. 65, 36471 Bražec, Bražec",
				"Jankovcova 8/1587d, 17000 Holešovice, Praha",
				"Jankovcova 8/1587d, 17000 Holešovice, Praha",
				"Jankovcova 8/1587d, 17000 Holešovice, Praha",
				"Jankovcova 8/1587, 17000 Holešovice, Praha",
				"č.p. 1, 36471 Javorná, Bražec"
		};

		try {
			for (int i = 0; i < testQueries.length; i++) {
				this.mockMvc.perform(get("/ruian/search?query={query}", testQueries[i]))
						.andDo(print())
						.andExpect(status().isOk())
						.andExpect(jsonPath("$").isArray())
						.andExpect(jsonPath("$.*", Matchers.hasSize(5)))
						.andExpect(jsonPath("$.[0]",
								Matchers.equalTo(expectedResults[i])));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
