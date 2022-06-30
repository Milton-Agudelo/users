package com.ada.users.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.ada.users.controller.user.UserDto;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Document(value="user")
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
    private String passwordHash;
    private List<RoleEnum> roles;
    private Date createdAt;

    public UserDocument(UserDto userDto) {
        this(UUID.randomUUID().toString(), userDto.getName(), userDto.getLastName(), userDto.getAge(),
                userDto.getEmail(), userDto.getPasswordHash(), userDto.getRoles(), new Date());
    }

}
