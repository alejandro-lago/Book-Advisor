package com.example.myapp.services;

import java.util.List;

import com.example.myapp.domain.Users;

public interface UserService {

    String cryptPassword(String password);
    Users create(Users user);
    Users getConnectedUser();
    Users getUser(String username);
    List<Users> getAllUsers();
    Users update(String username, Users newUser);
    void delete(String username);

}
