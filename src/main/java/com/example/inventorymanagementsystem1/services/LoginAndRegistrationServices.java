package com.example.inventorymanagementsystem1.services;

import com.example.inventorymanagementsystem1.model.User;

import java.util.Optional;

public interface LoginAndRegistrationServices {
    Optional<User> login(String email, String password);
    User saveUser(User user);
}
