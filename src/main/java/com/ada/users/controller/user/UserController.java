package com.ada.users.controller.user;

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

    @GetMapping("findById/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body((new UserDto(iUserService.findById(id))));
    }

    @GetMapping("findByEmail/{email}")
    public ResponseEntity<UserDto> findByEmail(@PathVariable String email){
        return ResponseEntity.status(HttpStatus.OK).body(new UserDto(iUserService.findByEmail(email)));
    }

    @PutMapping("updateById/{id}")
    public ResponseEntity<UserDto> updateById(@PathVariable String id, @RequestBody UserDto userDto) {
        return ResponseEntity.status(HttpStatus.OK).body(new UserDto(iUserService.updateById(id, new UserDocument(userDto))));
    }

    @PutMapping("updateByEmail/{email}")
    public ResponseEntity<UserDto> updateByEmail(@PathVariable String email, @RequestBody UserDto userDto) {
        return ResponseEntity.status(HttpStatus.OK).body(new UserDto(iUserService.updateByEmail(email, new UserDocument(userDto))));
    }

    @DeleteMapping("deleteById/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        iUserService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("deleteByEmail/{email}")
    public ResponseEntity<Void> deleteByEmail(@PathVariable String email) {
        iUserService.deleteByEmail(email);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}