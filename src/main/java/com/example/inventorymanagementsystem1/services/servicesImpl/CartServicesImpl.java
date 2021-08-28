package com.example.inventorymanagementsystem1.services.servicesImpl;

import com.example.inventorymanagementsystem1.model.Cart;
import com.example.inventorymanagementsystem1.repositories.CartRepository;
import com.example.inventorymanagementsystem1.services.CartServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CartServicesImpl implements CartServices {

    @Autowired
    private CartRepository cartRepo;


//    @Override
//    public List<Cart> listCartItems(long userId) {
//        return cartRepo.findByUser_Id(userId);
//    }

    @Override
    public void addToCart(Cart cart) {
        LocalDateTime localDateTime = LocalDateTime.now();
        cart.setDateTimePurchased(localDateTime);
        cartRepo.save(cart);
    }

    @Override
    public List<Cart> getPurchaseHistory(Long id) {
        return cartRepo.findAllByUser_Id(id);
    }
}
