package com.ada.users.exception;


import com.ada.users.error.ErrorCodeEnum;
import com.ada.users.error.InternalServerErrorException;
import org.springframework.http.HttpStatus;

public class RegisteredEmailException extends InternalServerErrorException {
    public RegisteredEmailException() {
        super(new ServerErrorResponseDto("Email already registered", ErrorCodeEnum.EMAIL_REGISTERED, HttpStatus.NOT_FOUND),
                HttpStatus.NOT_FOUND);
    }
}

