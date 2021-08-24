package com.example.inventorymanagementsystem1.repositories;

import com.example.inventorymanagementsystem1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
//    @Override
    Optional<User> findUserByEmailAndAndPassword(String email, String password);

}
