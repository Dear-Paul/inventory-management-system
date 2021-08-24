package com.example.inventorymanagementsystem1.services.servicesImpl;

import com.example.inventorymanagementsystem1.model.Book;
<<<<<<< HEAD
import com.example.inventorymanagementsystem1.model.User;
import com.example.inventorymanagementsystem1.repositories.BookRepository;
import com.example.inventorymanagementsystem1.repositories.UserRepository;
=======
import com.example.inventorymanagementsystem1.repositories.BookRepository;
>>>>>>> de63233dd7441924e8d48d60d6eddd4f8ee434cb
import com.example.inventorymanagementsystem1.services.AdminServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
<<<<<<< HEAD
    private UserRepository repository;
=======

>>>>>>> de63233dd7441924e8d48d60d6eddd4f8ee434cb
    @Override
    public List<Book> listOfBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Page<Book> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.bookRepository.findAll(pageable);
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
<<<<<<< HEAD
    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public User getUserById(long id) {
        return null;
    }

    @Override
    public void deleteUser(Long id) {
        User user = getUserById(id);
        repository.delete(user);
    }

=======
>>>>>>> de63233dd7441924e8d48d60d6eddd4f8ee434cb

}
