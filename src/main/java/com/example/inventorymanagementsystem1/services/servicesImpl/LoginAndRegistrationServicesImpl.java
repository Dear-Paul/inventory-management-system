package com.example.inventorymanagementsystem1.services.servicesImpl;

import com.example.inventorymanagementsystem1.model.User;
import com.example.inventorymanagementsystem1.repositories.UserRepository;
import com.example.inventorymanagementsystem1.services.LoginAndRegistrationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginAndRegistrationServicesImpl implements LoginAndRegistrationServices {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> login(String email, String password) {
        return userRepository.findUserByEmailAndAndPassword(email, password);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
