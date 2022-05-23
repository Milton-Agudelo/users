package com.ada.users.service;

import com.ada.users.model.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ImplUserService implements IUserService{
	private final HashMap<String, User> userHashMap = new HashMap<>();

	@Override
	public User createUser(User user) {
		userHashMap.put(user.getId(), user);
		return  user;
	}

	@Override
	public Optional<User> findById(String id) {
		User user = userHashMap.get(id);
		return user != null ? Optional.of(user) : Optional.empty();
	}

	@Override
	public List<User> findAll() {
		return new ArrayList(userHashMap.values());
	}
}
