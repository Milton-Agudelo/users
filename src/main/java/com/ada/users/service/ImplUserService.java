package com.ada.users.service;

import com.ada.users.entity.User;
import com.ada.users.repository.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImplUserService implements IUserService {

    private final IUserRepository iUserRepository;

    public ImplUserService(IUserRepository iUserRepository) {
        this.iUserRepository = iUserRepository;
    }

    @Override
    public User save(User user) {
        return iUserRepository.save(user);
    }

    @Override
    public Iterable<User> findAll() {
        return iUserRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return iUserRepository.findById(id).get();
    }

    @Override
    public boolean update(Long id, User user) {
        Optional<User> userToUpdate = iUserRepository.findById(id);
        boolean result = false;
        if (!userToUpdate.equals(Optional.empty())) {
            user.setId(id);
            iUserRepository.save(user);
            result = true;
        }
        return result;
    }

    @Override
    public boolean delete(Long id) {
        Optional<User> user = iUserRepository.findById(id);
        boolean result = false;
        if (!user.equals(Optional.empty())) {
            iUserRepository.delete(user.get());
            result = true;
        }
        return result;
    }

}