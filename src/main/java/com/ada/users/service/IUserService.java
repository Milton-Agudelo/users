package com.ada.users.service;

import com.ada.users.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    User createUser(User user);

    List<User> findAll();

    Optional<User> findById(String id);

    User update(User user);

    void delete(String id);

}