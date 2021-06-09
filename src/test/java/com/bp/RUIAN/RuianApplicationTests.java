package com.bp.RUIAN;

import com.bp.RUIAN.controllers.RuianController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RuianApplicationTests {

	@Autowired
	private RuianController ruianController;

	@Test
	void contextLoads() {
		Assertions.assertNotNull(ruianController);
	}

}
