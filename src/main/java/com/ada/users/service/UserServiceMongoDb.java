package com.ada.users.service;

import com.ada.users.exception.RegisteredEmailException;
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
    public UserDocument save(UserDocument userDocument) throws RegisteredEmailException{
        if (!iUserRepository.findFirstByEmail(userDocument.getEmail()).isPresent()) {
            return iUserRepository.save(userDocument);
        }

        throw new RegisteredEmailException();

    }

    @Override
    public UserDocument findById(String id) throws UserNotFoundException{

        Optional<UserDocument> documentFound = iUserRepository.findById(id);

        if (documentFound.isPresent()) {
            return documentFound.get();
        }

        throw new UserNotFoundException();

    }

    @Override
    public UserDocument findByEmail(String email) throws UserNotFoundException {

        Optional<UserDocument> documentFound = iUserRepository.findFirstByEmail(email);

        if (documentFound.isPresent()) {
            return documentFound.get();
        }

        throw new UserNotFoundException();
    }

    @Override
    public List<UserDocument> findAll() {
        return iUserRepository.findAll();
    }

    @Override
    public void deleteById(String id) throws UserNotFoundException {

        Optional<UserDocument> documentFound = iUserRepository.findById(id);

        if (documentFound.isPresent()) {
            iUserRepository.deleteById(id);

        }

        throw new UserNotFoundException();

    }

    @Override
    public void deleteByEmail(String email) throws UserNotFoundException {

        Optional<UserDocument> userToDelete = iUserRepository.findFirstByEmail(email);

        if (userToDelete.isPresent()) {
            iUserRepository.deleteById(userToDelete.get().getId());
        }

        throw new UserNotFoundException();

    }

    public UserDocument updateById(String id, UserDocument userDocument) throws UserNotFoundException {

        Optional<UserDocument> documentFound = iUserRepository.findById(id);

        if (documentFound.isPresent()) {
            userDocument.setId(id);
            iUserRepository.save(userDocument);

            return iUserRepository.findById(id).get();

        }

        throw new UserNotFoundException();

    }

    public UserDocument updateByEmail(String email,UserDocument userDocument) throws UserNotFoundException {

        if (iUserRepository.findFirstByEmail(email).isPresent()) {
            userDocument.setEmail(email);
            iUserRepository.save(userDocument);

            return iUserRepository.findFirstByEmail(email).get();
        }

        throw new UserNotFoundException();
    }

}