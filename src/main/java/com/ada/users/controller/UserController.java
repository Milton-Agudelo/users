package com.ada.users.controller;

import com.ada.users.controller.dto.UserDto;
import com.ada.users.model.User;
import com.ada.users.service.IUserService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/users")
public class UserController {
	/*@Autowired
    private IUserService iUserService;*/

	private final IUserService iUserService;

	public UserController(@Autowired IUserService iUserService) {
		this.iUserService = iUserService;
	}

	@GetMapping("/list")
	public List<User> findAll(){
		return iUserService.findAll();
	}

	@GetMapping(path = "/find/{id}")
	public Optional<User> findById(@PathVariable("id") String id){
		return iUserService.findById(id);
	}

	@PostMapping("/save/")
	public User createUser(@RequestBody UserDto userDto) {
		return iUserService.createUser(new User(userDto));
	}
}
