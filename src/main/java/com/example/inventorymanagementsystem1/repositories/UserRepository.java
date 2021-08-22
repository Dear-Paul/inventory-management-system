package com.example.inventorymanagementsystem1.repositories;

import com.example.inventorymanagementsystem1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
