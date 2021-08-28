package com.example.inventorymanagementsystem1.repositories;

import com.example.inventorymanagementsystem1.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUser_Id(Long userId);
    List<Cart> findAllByUser_Id(Long id);

}
