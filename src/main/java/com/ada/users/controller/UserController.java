package com.ada.users.controller;

import com.ada.users.controller.dto.UserDto;
import com.ada.users.entity.UserDocument;
import com.ada.users.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v3/users/")
public class UserController {
	/*@Autowired
    private IUserService iUserService;*/

    private final IUserService iUserService;

    public UserController(@Autowired IUserService iUserService) {
        this.iUserService = iUserService;
    }


    @GetMapping
    public ResponseEntity<List<UserDocument>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(iUserService.findAll());
    }

    @PostMapping
    private ResponseEntity<UserDocument> save(@RequestBody UserDto userDto) {
        return ResponseEntity.status(HttpStatus.OK).body(iUserService.save(new UserDocument(userDto)));
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDocument> findById(@PathVariable String id) {
        ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User with id: '" + id + "' can not be found!");
        try {
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(iUserService.findById(id));
     /*   } catch (Exception e) {
            throw new Exception(e.getMessage()); // */
        }  finally {
            return responseEntity;
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<UserDocument> update(@PathVariable String id, @RequestBody UserDto userDto) {
        ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User with id: '" + id + "' can not be found!");
        UserDocument userDocument = new UserDocument(userDto);
        if (iUserService.update(id, userDocument)) {
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(iUserService.findById(id));
        }

        return responseEntity;
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User with id: '" + id + "' can not be found!");
        if (iUserService.deleteById(id)) {
            responseEntity = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return responseEntity;
    }

}