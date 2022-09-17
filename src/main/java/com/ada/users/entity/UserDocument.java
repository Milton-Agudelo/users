package com.ada.users.entity;

import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.ada.users.controller.UserDto;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Document(value="user")
@AllArgsConstructor
@NoArgsConstructor
public class UserDocument  {

    @Id
    @NonNull
    private String id;
    @NonNull
    private String name;
    @NonNull
    private String lastName;
    @NonNull
    private Integer age;
    @Indexed( unique = true )
    @NonNull
    private String email;
    @NonNull
    private String passwordHash;
    @NonNull
    private List<RoleEnum> roles;
    @NonNull
    private Date createdAt;

    public UserDocument(UserDto userDto) {
        this(UUID.randomUUID().toString(), userDto.getName(), userDto.getLastName(), userDto.getAge(), userDto.getEmail(),
            userDto.getPassword() != null ? BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt()) : userDto.getPassword(),
            userDto.getRoles(), new Date());
    }

}
