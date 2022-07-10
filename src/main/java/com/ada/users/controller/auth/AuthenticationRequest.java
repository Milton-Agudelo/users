package com.ada.users.controller.auth;

import lombok.Data;

@Data
public class AuthenticationRequest {

    private String email;
    private String password;

}
