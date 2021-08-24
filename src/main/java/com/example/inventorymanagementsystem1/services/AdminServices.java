package com.example.inventorymanagementsystem1.services;

import com.example.inventorymanagementsystem1.model.Book;
<<<<<<< HEAD
import com.example.inventorymanagementsystem1.model.User;
=======
>>>>>>> de63233dd7441924e8d48d60d6eddd4f8ee434cb
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AdminServices {
    List<Book> listOfBooks ();
    Book getBookById (long id);
    Book addBookToStore (Book book);
    void removeBookFromStore (long id);
    Page<Book> findPaginated(int pageNo, int pageSize);
<<<<<<< HEAD
    List<User> getAllUsers();
    User getUserById (long id);
    void deleteUser(Long id);
=======
>>>>>>> de63233dd7441924e8d48d60d6eddd4f8ee434cb
}
