package com.ada.users.controller;

import com.ada.users.controller.dto.UserDto;
import com.ada.users.entity.User;
import com.ada.users.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
public class UserController {
	/*@Autowired
    private IUserService iUserService;*/

    private final IUserService iUserService;

    public UserController(@Autowired IUserService iUserService) {
        this.iUserService = iUserService;
    }


    @GetMapping("/")
    public ResponseEntity<Iterable<User>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(iUserService.findAll());
    }


    @PostMapping("/")
    private ResponseEntity<User> save(@RequestBody UserDto userDto) {
        return ResponseEntity.status(HttpStatus.OK).body(iUserService.save(new User(userDto)));
    }


    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User with id: '" + id + "' can not be found!");
        try {
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(iUserService.findById(id));
     /*   } catch (Exception e) {
            throw new Exception(e.getMessage()); // */
        }  finally {
            return responseEntity;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody UserDto userDto) {
        ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User with id: '" + id + "' can not be found!");
        User user = new User(userDto);
        if (iUserService.update(id, user)) {
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(iUserService.findById(id));
        }

        return responseEntity;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User with id: '" + id + "' can not be found!");
        if (iUserService.delete(id)) {
            responseEntity = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return responseEntity;
    }

}