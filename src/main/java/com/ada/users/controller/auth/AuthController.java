package com.ada.users.controller.auth;

import com.ada.users.controller.security.JWTGenerate;
import com.ada.users.entity.UserDocument;
import com.ada.users.exception.InvalidCredentialsException;
import com.ada.users.service.UserServiceMongoDb;
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
    private JWTGenerate jwtGenerate;
    @Autowired
    UserServiceMongoDb userService;
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody  AuthenticationRequest request){
        UserDocument userDocument = userService.findByEmail(request.getEmail());
        String jwt = "";
        if(BCrypt.checkpw(request.getPassword(), userDocument.getPasswordHash())){
            jwt = jwtGenerate.generateToken(userDocument);
            return new ResponseEntity<>(new AuthenticationResponse(jwt), HttpStatus.OK);
        } else {
            throw new InvalidCredentialsException();
        }
    }


}
