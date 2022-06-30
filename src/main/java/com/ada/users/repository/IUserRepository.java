package com.ada.users.repository;

import com.ada.users.entity.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface IUserRepository extends MongoRepository<UserDocument, String> {
    Optional<UserDocument> findFirstByEmail(String email);
}
