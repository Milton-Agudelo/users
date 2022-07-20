package com.ada.users.controller.auth;

import com.ada.users.controller.security.JwtGenerate;
import com.ada.users.entity.UserDocument;
import com.ada.users.exception.InvalidCredentialsException;
import com.ada.users.service.UserServiceDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {
    @Autowired
    private JwtGenerate jwtGenerate;
    @Autowired
    UserServiceDb userServiceDb;

    public AuthController(@Autowired UserServiceDb userServiceDb, @Autowired JwtGenerate jwtGenerate) {
        this.userServiceDb = userServiceDb;
        this.jwtGenerate = jwtGenerate;
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody  AuthenticationRequest request){
        UserDocument userDocument = userServiceDb.findByEmail(request.getEmail());
        String jwt = "";
        if(BCrypt.checkpw(request.getPassword(), userDocument.getPasswordHash())){
            jwt = jwtGenerate.generateToken(userDocument);
            return new ResponseEntity<>(new AuthenticationResponse(jwt), HttpStatus.OK);
        } else {
            throw new InvalidCredentialsException();
        }
    }

}
