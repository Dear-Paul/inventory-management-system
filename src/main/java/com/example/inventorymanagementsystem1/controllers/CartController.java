package com.example.inventorymanagementsystem1.controllers;

import com.example.inventorymanagementsystem1.model.Book;
import com.example.inventorymanagementsystem1.model.Cart;
import com.example.inventorymanagementsystem1.model.User;
import com.example.inventorymanagementsystem1.services.AdminServices;
import com.example.inventorymanagementsystem1.services.CartServices;
import com.example.inventorymanagementsystem1.services.CustomerServices;
import com.example.inventorymanagementsystem1.services.LoginAndRegistrationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartServices services;

    @Autowired
    private AdminServices adminServices;

    @Autowired
    private LoginAndRegistrationServices loginAndRegistrationServices;

    @Autowired
    private CustomerServices customerServices;

    @GetMapping("/addToCart/{id}")
    public String addToCart (@PathVariable(value = "id") Long id, Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        Book book = adminServices.getBookById(id);
        Cart cart = (Cart) session.getAttribute("cart");

        System.out.println(cart);
        if (cart.getBookList()== null) {
            Set<Book> bookList = new HashSet<>();
            bookList.add(book);
            cart.setBookList(bookList);
            cart.setAmount(cart.getAmount() + book.getPrice());

        } else {
            int count = 0;
            for (Book listOfBooks:cart.getBookList()) {
                if(listOfBooks.getName().equals(book.getName())){
                    count++;
                }
            }
            if(count == 0){
                cart.getBookList().add(book);
                cart.setAmount(cart.getAmount() + book.getPrice());
            }
        }

        return "redirect:/customer/";
    }

    @GetMapping("/show")
    public String showShoppingCart(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        User user1 = customerServices.getUserById(user.getId());
        Cart cart = (Cart) session.getAttribute("cart");
        cart.setUser(user);
        model.addAttribute("userCart", cart.getBookList());
        model.addAttribute("cart", cart);
        model.addAttribute("user", user1);
        return "cart";
    }

    @GetMapping("/remove/{id}")
    public String removeFromCart(@PathVariable(value = "id") long id, HttpSession session){
        Cart cart = (Cart) session.getAttribute("cart");
        Book book = adminServices.getBookById(id);
        Set<Book> bookList = cart.getBookList();
        Set<Book> bookList1 = new HashSet<>();
        for(Book books: bookList){
            if(!books.getName().equals(book.getName())){
                bookList1.add(books);
            }
        }
        cart.setBookList(bookList1);
        cart.setAmount(cart.getAmount() - book.getPrice());
       return "redirect:/cart/show";
    }

    @GetMapping("/purchase")
    public String purchaseBook(HttpSession session){
        Cart cart = (Cart) session.getAttribute("cart");
        User user = (User) session.getAttribute("user");
        User user1 = adminServices.getUserById(user.getId());
        System.out.println(user1.getId() +" "+ user1.getWalletBalance());
        if (user1.getWalletBalance() >= cart.getAmount()) {
            services.addToCart(cart);
            user1.setWalletBalance(user.getWalletBalance() - cart.getAmount());
            loginAndRegistrationServices.saveUser(user1);
            cart.setBookList(null);
            cart.setId(null);
            cart.setAmount(0);
            return "redirect:/customer/";
        } else {
            return "redirect:/customer/creditWallet";
        }
    }
}
