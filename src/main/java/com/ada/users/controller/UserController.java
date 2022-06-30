package com.ada.users.controller;

import com.ada.users.controller.dto.UserDto;
import com.ada.users.entity.UserDocument;
import com.ada.users.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

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
    public ResponseEntity<List<UserDto>> findAll() {
        List userDtoList = new ArrayList();
        ListIterator listIterator = iUserService.findAll().listIterator();
        while (listIterator.hasNext()) {
            userDtoList.add(new UserDto((UserDocument) listIterator.next()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(userDtoList);
    }

    @PostMapping
    private ResponseEntity<UserDto> save(@RequestBody UserDto userDto) {
        return ResponseEntity.status(HttpStatus.OK).body((new UserDto(iUserService.save(new UserDocument(userDto)))));
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDto> findById(@PathVariable String id) {
        ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User with id: '" + id + "' can not be found!");
        try {
            responseEntity = ResponseEntity.status(HttpStatus.OK).body((new UserDto(iUserService.findById(id))));
     /*   } catch (Exception e) {
            throw new Exception(e.getMessage()); // */
        }  finally {
            return responseEntity;
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<UserDto> update(@PathVariable String id, @RequestBody UserDto userDto) {
        ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User with id: '" + id + "' can not be found!");
        UserDocument userDocument = new UserDocument(userDto);
        if (iUserService.update(id, userDocument)) {
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(new UserDto(iUserService.findById(id)));
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