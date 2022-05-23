package com.ada.users.controller;

import com.ada.users.controller.dto.UserDto;
import com.ada.users.model.User;
import com.ada.users.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    public List<UserDto> findAll() {
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : iUserService.findAll()) {
            userDtos.add(new UserDto(user));
        }
        return userDtos;
    }

    @GetMapping("/find/{id}")
    public UserDto findById(@PathVariable String id) {
        return new UserDto(iUserService.findById(id).orElseThrow(IllegalArgumentException::new));
    }

    @PostMapping("/save/")
    public UserDto createUser(@RequestBody UserDto userDto) {
        return new UserDto(iUserService.createUser(new User(userDto)));
    }

    @PutMapping("/{id}")
    public UserDto update(@PathVariable String id, @RequestBody UserDto userDto) {
        userDto.setId(id);
        return new UserDto(iUserService.update(new User(userDto)));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        iUserService.delete(id);
    }

}