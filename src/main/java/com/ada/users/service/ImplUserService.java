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
	public String updateUser(User user) {
		String resultMessage = " el usuario con id: " + user.getId();
		try {
			if (!findById(user.getId()).equals(Optional.empty())) {
				userHashMap.replace(user.getId(), user);
				resultMessage = "Se actualizó" + resultMessage;
			} else {
				resultMessage = "No se encontró"  + resultMessage;
			}
			return resultMessage;
		} catch (Exception err){
			return "No se pudo actualizar"  + resultMessage;
		}
	}

	@Override
	public String deleteUser(String id) {
		String resultMessage = " el usuario con id: " + id;
		try {
			if (!findById(id).equals(Optional.empty())) {
				userHashMap.remove(id);
				resultMessage = "Se eliminó" + resultMessage;
			} else {
				resultMessage = "No se encontró"  + resultMessage;
			}
			return resultMessage;
		} catch (Exception err){
			return "No se pudo eliminar"  + resultMessage;
		}
	}

	@Override
	public List<User> findAll() {
		return new ArrayList(userHashMap.values());
	}
}
