package com.service;

import com.data.UsersRepository;
import com.exception.UserServiceException;
import com.io.UsersDatabase;
import com.model.User;

import java.util.Map;
import java.util.UUID;

public class UserServiceImpl implements UserService {

    UsersDatabase usersDatabase;
    UsersRepository usersRepository;

    public UserServiceImpl(UsersDatabase usersDatabase) {
        this.usersDatabase = usersDatabase;
    }

    @Override
    public String createUser(Map userDetails) {
        String userId = UUID.randomUUID().toString();
        userDetails.put("userId", userId);
        usersDatabase.save(userId, userDetails);
        return userId;
    }

    public User createUser(String firstName,
                           String lastName,
                           String email,
                           String password,
                           String repeatPassword) {
        if(firstName == null || firstName.trim().length() == 0) {
            throw new IllegalArgumentException("User's first name is empty");
        }

        if(lastName == null || lastName.trim().length() == 0) {
            throw new IllegalArgumentException("User's last name is empty");
        }
        User user = new User(firstName, lastName, email, UUID.randomUUID().toString());

        boolean isUserCreated;

        try {
            isUserCreated = usersRepository.save(user);
        } catch (RuntimeException ex) {
            throw new UserServiceException(ex.getMessage());
        }

        if(!isUserCreated) throw new UserServiceException("Could not create user");


        return user;
    }

    @Override
    public Map updateUser(String userId, Map userDetails) {
        Map existingUser = usersDatabase.find(userId);
        if(existingUser == null) throw new IllegalArgumentException("User not found");

        existingUser.put("firstName", userDetails.get("firstName"));
        existingUser.put("lastName", userDetails.get("lastName"));

        return usersDatabase.update(userId, existingUser);
    }

    @Override
    public Map getUserDetails(String userId) {
        return usersDatabase.find(userId);
    }

    @Override
    public void deleteUser(String userId) {
        Map existingUser = usersDatabase.find(userId);
        if(existingUser == null) throw new IllegalArgumentException("User not found");

        usersDatabase.delete(userId);
    }
}
