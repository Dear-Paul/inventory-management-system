package com.example.inventorymanagementsystem1.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/")
    public String homePage() {
        System.out.println("in home page");
        return "index";
    }

}
