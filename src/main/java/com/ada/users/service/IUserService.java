package com.ada.users.service;

import com.ada.users.entity.UserDocument;

import java.util.List;

public interface IUserService {

    UserDocument save(UserDocument userDocument);

    List<UserDocument> findAll();

    UserDocument findById(String id);

    UserDocument findByEmail(String email);
    boolean update(String id, UserDocument userDocument);

    boolean deleteById(String id);
}