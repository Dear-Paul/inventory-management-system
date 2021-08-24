package com.example.inventorymanagementsystem1.services;

import com.example.inventorymanagementsystem1.model.Book;
import com.example.inventorymanagementsystem1.model.User;
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
    List<User> getAllUsers();
    User getUserById (long id);
    void deleteUser(Long id);
}
