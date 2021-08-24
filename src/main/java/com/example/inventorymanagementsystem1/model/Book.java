package com.example.inventorymanagementsystem1.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;
    private String author;
    @Column(nullable = false, precision = 10, scale = 2)
    private double price;
    @Lob
    private byte[] picByte;

}
