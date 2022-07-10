package com.ada.users.exception;


import com.ada.users.error.ErrorCodeEnum;
import com.ada.users.error.InternalServerErrorException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends InternalServerErrorException {
    public UserNotFoundException() {
        super(new ServerErrorResponseDto("User not found", ErrorCodeEnum.USER_NOT_FOUND, HttpStatus.NOT_FOUND),
                HttpStatus.NOT_FOUND);
    }
}

