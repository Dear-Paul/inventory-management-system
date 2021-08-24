package com.example.inventorymanagementsystem1.services.servicesImpl;

import com.example.inventorymanagementsystem1.model.Book;
import com.example.inventorymanagementsystem1.repositories.BookRepository;
import com.example.inventorymanagementsystem1.services.AdminServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServicesImpl implements AdminServices {
    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Book> listOfBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(long id) {
        Optional<Book> book = bookRepository.findById(id);
        Book book1 = new Book();
        if (book.isPresent()) {
            book1 = book.get();
        }
        return book1;
    }

    @Override
    public Book addBookToStore(Book book) {
        return bookRepository.save(book);

    }

    @Override
    public void removeBookFromStore(long id) {
        Book book = getBookById(id);
        bookRepository.delete(book);
    }

}
