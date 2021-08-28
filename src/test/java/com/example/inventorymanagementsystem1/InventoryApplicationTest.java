package com.example.inventorymanagementsystem1;

import com.example.inventorymanagementsystem1.enums.UserType;
import com.example.inventorymanagementsystem1.model.Book;
import com.example.inventorymanagementsystem1.model.Cart;
import com.example.inventorymanagementsystem1.model.User;
import com.example.inventorymanagementsystem1.repositories.CartRepository;
import com.example.inventorymanagementsystem1.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = true)
public class InventoryApplicationTest {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private LoginAndRegistrationServices loginAndRegistrationServices;

    @Test
    public void testAddOneCartItem(){
        Book book = entityManager.find(Book.class, 1L);
        User user = entityManager.find(User.class, 3L);
        Cart newCartItem = new Cart();
        newCartItem.setUser(user);
        newCartItem.setBookList(Collections.singleton(book));
        newCartItem.setAmount(5000);

        Cart saveCartItem = cartRepository.save(newCartItem);

        assertTrue(saveCartItem.getId()>1);

    }

    @Test
    public void testGetCartItemsByUser(){
        User user = new User();
        user.setId(3L);
        List<Cart> cartItemsByUser =  cartRepository.findAllByUser_Id(user.getId());
        assertEquals(10, cartItemsByUser.size());
    }

    @Test
    public void testCustomerRegistration(){
        User user = new User();
        user.setUserType(UserType.ADMIN);
        user.setWalletBalance(500);
        user.setEmail("jin@gmail.com");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPassword("1234");
        User registration = userRepository.save(user);

        assertTrue(registration.getId()> 1);
        assertEquals(17,registration.getId());



    }

    @Test
    public void testLoginCredentials(){
        User user = entityManager.find(User.class, 3L);
        String email = user.getEmail();
        String password = user.getPassword();
        Optional <User> login = userRepository.findUserByEmailAndAndPassword(email, password);

        assertTrue(login.isPresent());
    }

}
