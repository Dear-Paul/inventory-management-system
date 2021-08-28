package com.example.inventorymanagementsystem1.services.servicesImpl;

import com.example.inventorymanagementsystem1.enums.UserType;
import com.example.inventorymanagementsystem1.model.Book;
import com.example.inventorymanagementsystem1.model.User;
import com.example.inventorymanagementsystem1.repositories.BookRepository;
import com.example.inventorymanagementsystem1.repositories.UserRepository;
import com.example.inventorymanagementsystem1.services.AdminServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServicesImpl implements AdminServices {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository repository;

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

    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public User getUserById(long id) {
        return repository.findUserById(id);
    }

    @Override
    public void deleteUser(Long id) {
        User user = getUserById(id);
        repository.delete(user);
    }

    @Override
    public List<User> getUsersByUserRole(UserType userType) {
        return repository.findAllByUserType(userType);
    }


}
