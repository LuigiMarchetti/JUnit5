package com.service;

import com.io.UsersDatabase;
import com.io.UsersDatabaseMapImpl;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceImplTest {

    //Simulating JUnit Test with database

    UsersDatabase usersDatabase;
    UserService userService;

    String createdUserId;

    @BeforeAll
    void setup() {
        // Create & initialize database
        usersDatabase = new UsersDatabaseMapImpl();
        usersDatabase.init();
        userService = new UserServiceImpl(usersDatabase);
    }

    @AfterAll
    void cleanup() {
        // Close connection
        // Delete database
        usersDatabase.close();
    }

    @Test
    @Order(1)
    @DisplayName("Create User works")
    void testCreateUser_whenProvidedWithValidDetails_returnsUserId() {
        Map<String, String> user = new HashMap<>();
        user.put("firstName", "Luigi");
        user.put("lastName", "Barichelli");

        createdUserId = userService.createUser(user);

        assertNotNull(createdUserId);

    }


    @Test
    @Order(2)
    @DisplayName("Update user works")
    void testUpdateUser_whenProvidedWithValidDetails_returnsUpdatedUserDetails() {
        //Assert
        Map<String, String> newUserDetails = new HashMap<>();
        newUserDetails.put("firstName", "Luigi");
        newUserDetails.put("lastName", "Marchetti");

        //Act
        Map updatedUserDetails = userService.updateUser(createdUserId, newUserDetails);

        //Arrange
        assertEquals(newUserDetails.get("firstName"), updatedUserDetails.get("firstName"));
        assertEquals(newUserDetails.get("lastName"), updatedUserDetails.get("lastName"));
    }

    @Test
    @Order(3)
    @DisplayName("Find user works")
    void testGetUserDetails_whenProvidedWithValidUserId_returnsUserDetails() {
        //Act
        Map userDetails = userService.getUserDetails(createdUserId);

        //Assert
        assertNotNull(userDetails, "user details should not be null");
        assertEquals(createdUserId, userDetails.get("userId"), "User found is not the same");
    }

    @Test
    @Order(4)
    @DisplayName("Delete user works")
    void testDeleteUser_whenProvidedWithValidUserId_returnsUserDetails() {
        //Act
        userService.deleteUser(createdUserId);

        //Assert
        assertNull(userService.getUserDetails(createdUserId), "User should not been found");
    }

}
