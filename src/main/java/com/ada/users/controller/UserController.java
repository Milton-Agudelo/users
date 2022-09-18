package com.ada.users.controller;

import com.ada.users.entity.UserDocument;
import com.ada.users.service.IUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
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

    @ApiIgnore
    @GetMapping
    public ModelAndView testResult() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping("findAll/")
    public ResponseEntity<List<UserDto>> findAll() {
        List<UserDto> userDtoList = new ArrayList<>();

        for (UserDocument userDoc : iUserService.findAll()) {
            userDtoList.add(new UserDto(userDoc));
        }
        
        return ResponseEntity.status(HttpStatus.OK).body(userDtoList);
    }

    @PostMapping("save/")
    public ResponseEntity<UserDto> save(@RequestBody UserDto userDto) {
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
        userDto.setId(id);
        UserDocument userDocument = new UserDocument(id, userDto.getName(), userDto.getLastName(),
                userDto.getAge(), userDto.getEmail(), userDto.getPassword(), userDto.getRoles(), userDto.getDate());
        return ResponseEntity.status(HttpStatus.OK).body(new UserDto(iUserService.updateById(id, userDocument)));
    }

    @PutMapping("updateByEmail/{email}")
    public ResponseEntity<UserDto> updateByEmail(@PathVariable String email, @RequestBody UserDto userDto) {
        UserDocument userDocument = new UserDocument(userDto.getId(), userDto.getName(), userDto.getLastName(),
                userDto.getAge(), email, userDto.getPassword(), userDto.getRoles(), userDto.getDate());
        return ResponseEntity.status(HttpStatus.OK).body(new UserDto(iUserService.updateByEmail(email, userDocument)));
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

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> NullPointerException() {
        return ResponseEntity.status(HttpStatus.IM_USED).body("Invalid data request");
    }

}