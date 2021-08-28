package com.example.inventorymanagementsystem1.services;

import com.example.inventorymanagementsystem1.model.Cart;

import java.util.List;

public interface CartServices {

//    List<Cart> listCartItems(long userId);
    void addToCart (Cart cart);
    List<Cart> getPurchaseHistory(Long id);

}
