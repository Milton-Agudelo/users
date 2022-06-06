package com.ada.users.controller.dto;

import com.ada.users.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {

    private String name;
    private int age;
    private String email;

    public UserDto(User user) {
        this(user.getName(), user.getAge(), user.getEmail());
    }

}