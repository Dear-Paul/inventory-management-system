package com.example.inventorymanagementsystem1.controllers;

import com.example.inventorymanagementsystem1.dto.UserDto;
import com.example.inventorymanagementsystem1.enums.UserType;
import com.example.inventorymanagementsystem1.model.Book;
import com.example.inventorymanagementsystem1.model.User;
import com.example.inventorymanagementsystem1.repositories.BookRepository;
import com.example.inventorymanagementsystem1.services.CustomerServices;
import com.example.inventorymanagementsystem1.services.LoginAndRegistrationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
public class LoginController {
    @Autowired
    private LoginAndRegistrationServices loginAndRegistrationServices;
    @Autowired
    CustomerServices customerServices;
    @Autowired
    private BookRepository bookRepository;


    @GetMapping("/")
    public String homepage(Model model){
        List<Book> bookList = bookRepository.findAll();
        model.addAttribute("listOfBooks", bookList);
        return "index";
    }

    @GetMapping("/login")
    public String getLoginInfo( Model model){
        UserDto userDto = new UserDto();
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("login", userDto);
        return "loginAndRegistration";
    }


    @PostMapping("/tested")
    public String login(@ModelAttribute("login") UserDto userDto, Model model,HttpSession session){
        Optional<User> optionalUser = loginAndRegistrationServices.login(userDto.getEmail(), userDto.getPassword());
        if(optionalUser.isPresent()){
            User user1 = optionalUser.get();
            session.setAttribute("user", user1);
            if(user1.getUserType().equals(UserType.ADMIN)){
                System.out.println("i got here");
                return "adminDashboard";
            } else {
                return "customerDashboard";
            }
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/register")
    public String registration(@ModelAttribute("user") User user){
        user.setUserType(UserType.CUSTOMER);
        loginAndRegistrationServices.saveUser(user);
        return "redirect:/login";

    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }

}
