package com.example.inventorymanagementsystem1.services.servicesImpl;
import com.example.inventorymanagementsystem1.model.User;
import com.example.inventorymanagementsystem1.repositories.UserRepository;
import com.example.inventorymanagementsystem1.services.CustomerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class CustomerServicesImpl implements CustomerServices {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserById(long id) {
        return userRepository.findUserById(id);
    }
}
