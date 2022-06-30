package com.ada.users.model;

import com.ada.users.controller.user.UserDto;
import java.util.UUID;

public class User {

	private String name;
	private String id;
	private String email;

	public User(String name, String email) {
		this.id = UUID.randomUUID().toString();
		this.name = name;
		this.email = email;
	}
	public User(UserDto userDto) {
		this(userDto.getName(), userDto.getEmail());
	}

	public User(String id, String name, String email) {
		this.id = id;
		this.name = name;
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
