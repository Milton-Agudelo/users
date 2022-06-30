package com.ada.users.service;

import com.ada.users.exception.UserNotFoundException;
import com.ada.users.repository.IUserRepository;
import com.ada.users.entity.UserDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceMongoDb implements IUserService {

    private final IUserRepository iUserRepository;

    public UserServiceMongoDb(@Autowired IUserRepository iUserRepository) {
        this.iUserRepository = iUserRepository;
    }

    @Override
    public UserDocument save(UserDocument userDocument) {
        return iUserRepository.save(userDocument);
    }

    @Override
    public UserDocument findById(String id) {
        return iUserRepository.findById(id).get();
    }

    @Override
    public UserDocument findByEmail(String email) throws UserNotFoundException {

            if(!email.equals("")){
                Optional<UserDocument> userDocument = iUserRepository.findFirstByEmail(email);
                if (userDocument.isPresent()){
                    return userDocument.get();
                } else {
                    throw new UserNotFoundException();
                }
            }
            throw new UserNotFoundException();
        }

    @Override
    public List<UserDocument> findAll() {
        return iUserRepository.findAll();
    }

    @Override
    public boolean deleteById(String id) {
        boolean result = false;
        Optional<UserDocument> user = iUserRepository.findById(id);
        if (!user.equals(Optional.empty())) {
            iUserRepository.deleteById(id);
            result = true;
        }
        return result;
    }

    public boolean update(String id, UserDocument userDocument) {
        boolean result = false;
        Optional<UserDocument> userToUpdate = iUserRepository.findById(id);
        if (!userToUpdate.equals(Optional.empty())) {
            userDocument.setId(id);
            iUserRepository.save(userDocument);
            result = true;
        }
        return result;
    }

}