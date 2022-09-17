package com.ada.users.controller;

import static org.hamcrest.MatcherAssert.assertThat;

import com.ada.users.entity.RoleEnum;
import com.ada.users.exception.RegisteredEmailException;
import com.ada.users.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserControllerTest {

    final String randString = "abcdefghijklmnopqrstuvwxyz12345674890";

    Random rand = new Random();

    @Autowired
    UserController userController;

    protected String getRandomString(int minimum) {
        StringBuilder randomString = new StringBuilder();
        int length = rand.nextInt(4)+minimum;
        while (randomString.length() < length) { // length of the random string.
            int index = (int) (rand.nextFloat() * randString.length());
            randomString.append(randString.charAt(index));
        }
        return randomString.toString();
    }

    private UserDto getRandomUserDto() {
        List<RoleEnum> roles = Arrays.asList(new RoleEnum[]{RoleEnum.USER});
        String name = getRandomString(4);
        String lastName = getRandomString(4);
        int age = rand.nextInt(99);
        String password = getRandomString(8);
        String email = name + "_" + lastName + "@gmail.com";
        return new UserDto(null, name, lastName, age, email, password, roles, new Date());
    }

// ---------------------------------------------------------
// Create User Tests
// ---------------------------------------------------------

    @Test
    void testCreateUserSuccessfully() {
        // Arrange
        UserDto userDto = getRandomUserDto();

        // Act
        UserDto savedUser = userController.save(userDto).getBody();

        //Assert
        assertThat(savedUser.getEmail(), is(userDto.getEmail()));
        assertThat(savedUser.getName(), is(userDto.getName()));
        assertThat(savedUser.getLastName(), is(userDto.getLastName()));
        assertEquals(savedUser.getAge(), userDto.getAge());
        assertThat(savedUser.getRoles(), is(userDto.getRoles()));
    }

    @Test
    void testCreateUserRegisteredEmailException() {
        // Arrange
        UserDto userDto = getRandomUserDto();
        userDto.setEmail("ada29@gmail.com");

        // Act & assert
        assertThrows(RegisteredEmailException.class,
                ()->{
                    userController.save(userDto);
                });
    }

    @Test
    void testCreateUserWithoutEmailFailed() {
        // Arrange
        UserDto userDto = getRandomUserDto();
        userDto.setEmail(null);

        String expectedExceptionMessage = "email is marked non-null but is null";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                ()->{
                    userController.save(userDto);
                });

        // Assert
        assertEquals(expectedExceptionMessage, exception.getMessage(), "Illegal argument exception message");
    }

    @Test
    void testCreateUserWithoutAgeFailed() {
        // Arrange
        UserDto userDto = getRandomUserDto();
        userDto.setAge(null);

        String expectedExceptionMessage = "age is marked non-null but is null";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                ()->{
                    userController.save(userDto);
                });

        // Assert
        assertEquals(expectedExceptionMessage, exception.getMessage(), "Illegal argument exception message");
    }

    @Test
    void testCreateUserWithoutPasswordFailed() {
        // Arrange
        UserDto userDto = getRandomUserDto();
        userDto.setPassword(null);

        String expectedExceptionMessage = "passwordHash is marked non-null but is null";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            ()->{
                userController.save(userDto);
            });

        // Assert
        assertEquals(expectedExceptionMessage, exception.getMessage(), "Illegal argument exception message");
    }

    @Test
    void testCreateUserWithoutNameFailed() {
        // Arrange
        UserDto userDto = getRandomUserDto();
        userDto.setName(null);
        String expectedExceptionMessage = "name is marked non-null but is null";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            ()->{
                userController.save(userDto);
            });

        // Assert
        assertEquals(expectedExceptionMessage, exception.getMessage(), "Illegal argument exception message");
    }

// ---------------------------------------------------------
// Query All Users Tests
// ---------------------------------------------------------

    @Test
    void testQueryAllUsersSuccessfully() {

        // Arrange

        // Act
        ResponseEntity<List<UserDto>> usersList = userController.findAll();
        int usersListSize = usersList.getBody().size();

        //Assert
        assertThat("users list size: ", usersListSize, greaterThan(0));
        int randPos = rand.nextInt(usersListSize-1);

        //Assert
        assertThat("Object type", usersList, instanceOf(ResponseEntity.class));
        assertThat("Object type", usersList.getBody(), instanceOf(List.class));
        assertThat("Object type", usersList.getBody().get(randPos), instanceOf(UserDto.class));

        for (UserDto user: usersList.getBody()) {
            assertThat("Id", user.getId(), is(notNullValue()));
            assertThat("Name", user.getName(), is(notNullValue()));
            assertThat("Last Name", user.getLastName(), is(notNullValue()));
            assertThat("Age", user.getAge(), is(notNullValue()));
            assertThat("Email", user.getEmail(), is(notNullValue()));
            assertThat("Password hash", user.getPassword(), is(notNullValue()));
            assertThat("Roles", user.getRoles().size(), is(greaterThan(0)));
            assertThat("Date", user.getDate(), is(notNullValue()));
        }

    }

    @Test
    void testQueryAllUsersWithVoidDB() {

        // Arrange

        // Act
        ResponseEntity<List<UserDto>> usersList = userController.findAll();


        //Assert


    }

// ---------------------------------------------------------
// Query by Id Tests
// ---------------------------------------------------------

    @Test
    void testQueryByIdSuccessfully() {

        // Arrange
        ResponseEntity<List<UserDto>> usersList = userController.findAll();
        int usersListSize = usersList.getBody().size();
        int randPos = rand.nextInt(usersListSize-1);

        UserDto userToQuery = usersList.getBody().get(randPos);

        // Act
        ResponseEntity<UserDto> obtainedUser = userController.findById(userToQuery.getId());

        //Assert
        assertThat("Obtained user: ", obtainedUser, is(notNullValue()));
        assertThat("Obtained user name: ", obtainedUser.getBody().getName(), is(userToQuery.getName()));
        assertThat("Obtained user last name: ", obtainedUser.getBody().getLastName(), is(userToQuery.getLastName()));
        assertThat("Obtained user email: ", obtainedUser.getBody().getEmail(), is(userToQuery.getEmail()));
        assertThat("Obtained user age: ", obtainedUser.getBody().getAge(), is(userToQuery.getAge()));
        assertThat("Obtained user password: ", obtainedUser.getBody().getPassword(), is(userToQuery.getPassword()));
        assertThat("Obtained user date: ", obtainedUser.getBody().getDate(), is(userToQuery.getDate()));
    }

    @Test
    void testQueryByIdFailed() {

        // Arrange
        String id = UUID.randomUUID().toString();
        String expectedExceptionMessage = "User not found";

        // Act & Assert
        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                ()->{
                    userController.findById(id);
                });

        // Assert
        assertEquals(expectedExceptionMessage, exception.getServerErrorResponseDto().getMessage(), "UserNotFound exception message");
    }

// ---------------------------------------------------------
// Query by Email Tests
// ---------------------------------------------------------

    @Test
    void testQueryByEmailSuccessfully() {

        // Arrange
        ResponseEntity<List<UserDto>> usersList = userController.findAll();
        int usersListSize = usersList.getBody().size();
        int randPos = rand.nextInt(usersListSize-1);

        UserDto userToQuery = usersList.getBody().get(randPos);

        // Act
        ResponseEntity<UserDto> obtainedUser = userController.findByEmail(userToQuery.getEmail());

        //Assert
        assertThat("Obtained user: ", obtainedUser, is(notNullValue()));
        assertThat("Obtained user name: ", obtainedUser.getBody().getName(), is(userToQuery.getName()));
        assertThat("Obtained user last name: ", obtainedUser.getBody().getLastName(), is(userToQuery.getLastName()));
        assertThat("Obtained user email: ", obtainedUser.getBody().getEmail(), is(userToQuery.getEmail()));
        assertThat("Obtained user age: ", obtainedUser.getBody().getAge(), is(userToQuery.getAge()));
        assertThat("Obtained user password: ", obtainedUser.getBody().getPassword(), is(userToQuery.getPassword()));
    }

    @Test
    void testQueryByEmailFailed() {

        // Arrange
        String name = getRandomString(4);
        String lastName = getRandomString(4);
        String email = name.toUpperCase() + "_" + lastName + "@gmail.com";
        String expectedExceptionMessage = "User not found";

        // Act & Assert
        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                ()->{
                    ResponseEntity<UserDto> obtainedUser = userController.findByEmail(email);
                });

        // Assert
        assertEquals(expectedExceptionMessage, exception.getServerErrorResponseDto().getMessage(), "UserNotFound exception message");
    }

// ---------------------------------------------------------
// Update by Id Tests
// ---------------------------------------------------------

    @Test
    void testUpdateByIdSuccessfully() {

        // Arrange
        ResponseEntity<List<UserDto>> usersList = userController.findAll();
        int randPos = rand.nextInt(usersList.getBody().size()-1);

        UserDto userToUpdate = usersList.getBody().get(randPos);
        String userId = userToUpdate.getId();
        String userPassword = userToUpdate.getPassword();

        UserDto userUpdated = getRandomUserDto();
        userUpdated.setId(userId);
        userUpdated.setPassword(userPassword);

        // Act
        UserDto obtainedUser = userController.updateById(userId, userUpdated).getBody();

        //Assert
        assertThat("Obtained user: ", obtainedUser, is(notNullValue()));
        assertThat("Obtained user id: ", obtainedUser.getId(), is(userId));
        assertThat("Obtained user name: ", obtainedUser.getName(), is(userUpdated.getName()));
        assertThat("Obtained user last name: ", obtainedUser.getLastName(), is(userUpdated.getLastName()));
        assertThat("Obtained user email: ", obtainedUser.getEmail(), is(userUpdated.getEmail()));
        assertThat("Obtained user age: ", obtainedUser.getAge(), is(userUpdated.getAge()));
        assertThat("Obtained user password: ", obtainedUser.getPassword(), is(userUpdated.getPassword()));
    }

    @Test
    void testUpdateByIdFailed() {

        // Arrange
        String id = UUID.randomUUID().toString();
        String expectedExceptionMessage = "User not found";
        UserDto userToUpdate = getRandomUserDto();
        userToUpdate.setId(id);

        // Act & Assert
        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                ()->{
                    userController.updateById(id, userToUpdate);
                });

        // Assert
        assertEquals(expectedExceptionMessage, exception.getServerErrorResponseDto().getMessage(),
                "UserNotFound exception message");
    }

// ---------------------------------------------------------
// Update by Email Tests
// ---------------------------------------------------------

    @Test
    void testUpdateByEmailSuccessfully() {

        // Arrange
        ResponseEntity<List<UserDto>> usersList = userController.findAll();
        int randPos = rand.nextInt(usersList.getBody().size()-1);

        String userId = usersList.getBody().get(randPos).getId();
        String userEmail = usersList.getBody().get(randPos).getEmail();

        UserDto userUpdated = getRandomUserDto();
        userUpdated.setId(userId);
        userUpdated.setEmail(userEmail);

        // Act
        ResponseEntity<UserDto> obtainedUser = userController.updateByEmail(userEmail, userUpdated);

        //Assert
        assertThat("Obtained user: ", obtainedUser, is(notNullValue()));
        assertThat("Obtained user id: ", obtainedUser.getBody().getId(), is(userUpdated.getId()));
        assertThat("Obtained user name: ", obtainedUser.getBody().getName(), is(userUpdated.getName()));
        assertThat("Obtained user last name: ", obtainedUser.getBody().getLastName(), is(userUpdated.getLastName()));
        assertThat("Obtained user email: ", obtainedUser.getBody().getEmail(), is(userEmail));
        assertThat("Obtained user age: ", obtainedUser.getBody().getAge(), is(userUpdated.getAge()));
        assertThat("Obtained user password: ", obtainedUser.getBody().getPassword(), is(userUpdated.getPassword()));
    }

    @Test
    void testUpdateByEmailFailed() {

        // Arrange
        String id = UUID.randomUUID().toString();
        String expectedExceptionMessage = "User not found";
        UserDto userToUpdate = getRandomUserDto();
        userToUpdate.setId(id);

        // Act & Assert
        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                ()->{
                    userController.updateById(id, userToUpdate);
                });

        // Assert
        assertEquals(expectedExceptionMessage, exception.getServerErrorResponseDto().getMessage(),
                "UserNotFound exception message");
    }

// ---------------------------------------------------------
// Delete by Id Tests
// ---------------------------------------------------------

    @Test
    void testDeleteByIdSuccessfully() {

        // Arrange
        UserDto userToDelete = getRandomUserDto();
        userController.save(userToDelete);
        userToDelete = userController.findByEmail(userToDelete.getEmail()).getBody();

        // Act
        ResponseEntity<Void> response = userController.deleteById(userToDelete.getId());

        //Assert
        assertThat("Obtained user: ", response.getStatusCode(), is(HttpStatus.NO_CONTENT));

    }

    @Test
    void testDeleteByIdFailed() {

        // Arrange
        String id = UUID.randomUUID().toString();
        String expectedExceptionMessage = "User not found";

        // Act & Assert
        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                ()->{
                    userController.deleteById(id);
                });

        // Assert
        assertEquals(expectedExceptionMessage, exception.getServerErrorResponseDto().getMessage(),
                "UserNotFound exception message");
    }

// ---------------------------------------------------------
// Delete by Email Tests
// ---------------------------------------------------------

    @Test
    void testDeleteByEmailSuccessfully() {

        // Arrange
        UserDto userToDelete = getRandomUserDto();
        userController.save(userToDelete);
        userToDelete = userController.findByEmail(userToDelete.getEmail()).getBody();

        // Act
        ResponseEntity<Void> response = userController.deleteByEmail(userToDelete.getEmail());

        //Assert
        assertThat("Obtained user: ", response.getStatusCode(), is(HttpStatus.NO_CONTENT));

    }

    @Test
    void testDeleteByEmailFailed() {

        // Arrange
        String name = getRandomString(4);
        String lastName = getRandomString(4);
        String email = name.toUpperCase() + "_" + lastName + "@gmail.com";
        String expectedExceptionMessage = "User not found";

        // Act & Assert
        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                ()->{
                    userController.deleteByEmail(email);
                });

        // Assert
        assertEquals(expectedExceptionMessage, exception.getServerErrorResponseDto().getMessage(),
                "UserNotFound exception message");
    }
}