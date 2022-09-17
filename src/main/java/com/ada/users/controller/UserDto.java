package com.ada.users.controller;

import com.ada.users.entity.RoleEnum;
import com.ada.users.entity.UserDocument;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;
import javax.validation.constraints.Email;

@Data
@AllArgsConstructor
public class UserDto {

    private String id;
    private String name;
    @JsonProperty("lastName")
    private String lastName;
    private Integer age;
    @Email
    private String email;
    @JsonProperty("passwordHash")
    private String password;
    private List<RoleEnum> roles;
    private Date date;

    public UserDto(UserDocument userDocument) {
        this(userDocument.getId(), userDocument.getName(), userDocument.getLastName(), userDocument.getAge(),
             userDocument.getEmail(), userDocument.getPasswordHash(), userDocument.getRoles(), userDocument.getCreatedAt());
    }

}