package com.example.inventorymanagementsystem1.repositories;

import com.example.inventorymanagementsystem1.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
