package com.ada.users.service;

import com.ada.users.entity.UserDocument;

import java.util.List;

public interface IUserService {

    UserDocument save(UserDocument userDocument);

    List<UserDocument> findAll();

    UserDocument findById(String id);

    UserDocument findByEmail(String email);
    UserDocument updateById(String id, UserDocument userDocument);

    UserDocument updateByEmail(String email, UserDocument userDocument);

    void deleteById(String id);

    void deleteByEmail(String email);
}