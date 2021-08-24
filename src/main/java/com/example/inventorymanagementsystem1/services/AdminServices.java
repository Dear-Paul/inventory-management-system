package com.example.inventorymanagementsystem1.services;

import com.example.inventorymanagementsystem1.model.Book;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AdminServices {
    List<Book> listOfBooks ();
    Book getBookById (long id);
    Book addBookToStore (Book book);
    void removeBookFromStore (long id);
}
