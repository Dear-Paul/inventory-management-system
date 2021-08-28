package com.example.inventorymanagementsystem1.model;

import com.example.inventorymanagementsystem1.enums.UserType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserType userType;
    @Column(columnDefinition = "double precision default 0")
    private double walletBalance;

    public User(){

    }

    public User(String firstName, String lastName, String email, String password, UserType userType, double walletBalance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.walletBalance = walletBalance;
    }
}
