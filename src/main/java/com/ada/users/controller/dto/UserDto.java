package com.ada.users.controller.dto;

import com.ada.users.entity.UserDocument;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDto {

    private String name;
    private String lastName;
    private int age;
    private String email;

    public UserDto(UserDocument userDocument) {
        this(userDocument.getName(), userDocument.getLastName(), userDocument.getAge(), userDocument.getEmail());
    }

}