package com.ada.users.controller.dto;

import com.ada.users.model.User;

public class UserDto {
    private String name;
    private String id;
    private String email;

    public UserDto(String name, String id, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public UserDto(User user) {
        this(user.getName(), user.getId(), user.getEmail());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}