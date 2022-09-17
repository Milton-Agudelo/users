package com.ada.users;

import static org.assertj.core.api.Assertions.assertThat;

import com.ada.users.controller.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UsersApplicationTests {

	@Autowired
	private UserController controller;
	@Test
	void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
	}

}
