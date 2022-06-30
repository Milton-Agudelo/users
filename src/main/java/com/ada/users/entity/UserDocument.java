package com.ada.users.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.ada.users.controller.dto.UserDto;

import java.util.Date;
import java.util.UUID;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class UserDocument  {

    @Id
    private String id;
    private String name;
    private String lastName;
    private int age;
    @Indexed( unique = true )
    private String email;
    private Date createdAt;

    public UserDocument(UserDto userDto) {
        this(UUID.randomUUID().toString(), userDto.getName(), userDto.getLastName(), userDto.getAge(), userDto.getEmail(), new Date());
    }

}
