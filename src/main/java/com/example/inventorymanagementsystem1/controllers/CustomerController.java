package com.example.inventorymanagementsystem1.controllers;

import com.example.inventorymanagementsystem1.enums.UserType;
import com.example.inventorymanagementsystem1.model.Book;
import com.example.inventorymanagementsystem1.model.User;
import com.example.inventorymanagementsystem1.services.AdminServices;
import com.example.inventorymanagementsystem1.services.CustomerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerServices customerServices;
    @Autowired
    private AdminServices adminServices;


    @GetMapping("/")
    public ModelAndView customerDashboard (Model model) {
//        model.addAttribute("listOfBooks", adminServices.listOfBooks());

        return findPaginated(1);
    }


    @GetMapping("/page/{pageNo}")
    public ModelAndView findPaginated(@PathVariable(value = "pageNo") int pageNo) {
        int pageSize = 6;
        ModelAndView model = new ModelAndView("customerDashboard");

        return getModelAndView(pageNo, pageSize, model, adminServices);
    }

    public static ModelAndView getModelAndView(@PathVariable("pageNo") int pageNo, int pageSize, ModelAndView model, AdminServices adminServices) {
        Page<Book> page = adminServices.findPaginated(pageNo, pageSize);
        List< Book > bookList = page.getContent();

        model.addObject("currentPage", pageNo);
        model.addObject("totalPages", page.getTotalPages());
        model.addObject("totalItems", page.getTotalElements());
        model.addObject("listOfBooks", bookList);
        System.out.println("in pagination");
        return model;
    }

}
