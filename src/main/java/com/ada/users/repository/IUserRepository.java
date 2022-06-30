package com.ada.users.repository;

import com.ada.users.entity.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IUserRepository extends MongoRepository<UserDocument, String> {

}
