package com.example.inventorymanagementsystem1.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToMany(targetEntity = Book.class)
    private Set<Book> bookList;
    @ManyToOne
    private User user;
    private double amount;
    private LocalDateTime dateTimePurchased;


}
