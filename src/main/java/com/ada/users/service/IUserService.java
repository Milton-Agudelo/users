package com.ada.users.service;

import com.ada.users.model.User;
import java.util.List;
import java.util.Optional;

public interface IUserService {

	User createUser(User user);
	String updateUser(User user);
	String deleteUser(String id);
	List<User> findAll();
	Optional<User> findById(String id);
}
