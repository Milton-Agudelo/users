package com.ada.users.controller;

import com.ada.users.controller.auth.AuthController;
import com.ada.users.controller.auth.AuthenticationRequest;
import com.ada.users.controller.security.JwtGenerate;
import com.ada.users.exception.InvalidCredentialsException;
import com.ada.users.service.UserServiceDb;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class AuthControllerTest {

    private String jwt = "Bearer ";

    @Autowired
    private JwtGenerate jwtGenerate;

    @Autowired
    private UserServiceDb userServiceDb;

    @Test
    void testInvalidCredentials() {
        // Arrange
        AuthenticationRequest authRequest = new AuthenticationRequest();
        authRequest.setEmail("m6w2bf_u9w12@gmail.com");
        authRequest.setPassword("123456");

        AuthController authController = new AuthController(userServiceDb, jwtGenerate);

        // Act & assert
        InvalidCredentialsException exception = assertThrows(InvalidCredentialsException.class,
                ()-> {
                    authController.login(authRequest);
                });

    }

}
