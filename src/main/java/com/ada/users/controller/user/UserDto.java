package com.ada.users.controller.user;

import com.ada.users.entity.RoleEnum;
import com.ada.users.entity.UserDocument;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
@AllArgsConstructor
public class UserDto {

    private String id;
    private String name;
    private String lastName;
    private int age;
    private String email;
    private String passwordHash;
    private List<RoleEnum> roles;
    private Date date;

    public UserDto(UserDocument userDocument) {
        this(userDocument.getId(), userDocument.getName(), userDocument.getLastName(), userDocument.getAge(),
             userDocument.getEmail(), userDocument.getPasswordHash(), userDocument.getRoles(), userDocument.getCreatedAt());
    }

}