package com.ada.users.service;

import com.ada.users.entity.User;

public interface IUserService {

    User save(User user);

    Iterable<User> findAll();

    User findById(Long id);

    boolean update(Long id, User user);

    boolean delete(Long id);
}