package com.example.inventorymanagementsystem1.controllers;

import com.example.inventorymanagementsystem1.enums.UserType;
import com.example.inventorymanagementsystem1.model.Book;
import com.example.inventorymanagementsystem1.model.Cart;
import com.example.inventorymanagementsystem1.model.User;
import com.example.inventorymanagementsystem1.services.AdminServices;
import com.example.inventorymanagementsystem1.services.CartServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Value("${uploadDir}")
    private String uploadFolder;

    @Autowired
    private AdminServices adminServices;
    @Autowired
    private CartServices cartServices;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/")
    public ModelAndView adminHomePage(Model model) {
//        model.addAttribute("books", adminServices.listOfBooks());
        return findPaginated(1);
    }

    @GetMapping("/page/{pageNo}")
    public ModelAndView findPaginated(@PathVariable(value = "pageNo") int pageNo) {
        int pageSize = 6;
        ModelAndView model = new ModelAndView("adminDashboard");

        return getModelAndView(pageNo, pageSize, model, adminServices);
    }

    public ModelAndView getModelAndView(@PathVariable("pageNo") int pageNo, int pageSize, ModelAndView model, AdminServices adminServices) {
        Page<Book> page = adminServices.findPaginated(pageNo, pageSize);
        List< Book > bookList = page.getContent();

        model.addObject("currentPage", pageNo);
        model.addObject("totalPages", page.getTotalPages());
        model.addObject("totalItems", page.getTotalElements());
        model.addObject("listOfBooks", bookList);
        System.out.println("in pagination");
        return model;
    }

    @GetMapping("/books")
    public String getAllBooks (Model model) {
        List<Book> bookList = adminServices.listOfBooks();
        model.addAttribute("listOfBooks", bookList);
        return "adminViewAllBooks";
    }

    @GetMapping("/books/add")
    public String addBook (Model model) {
        model.addAttribute("book", new Book());
        return "adminAddNewBook";
    }

    @PostMapping("/books/add")
    public @ResponseBody ResponseEntity<?> createBook(Book book, Model model, HttpServletRequest request
            ,final @RequestParam("image") MultipartFile file) {
        try {
            //String uploadDirectory = System.getProperty("user.dir") + uploadFolder;
            String uploadDirectory = request.getServletContext().getRealPath(uploadFolder);
            log.info("uploadDirectory:: " + uploadDirectory);
            String fileName = file.getOriginalFilename();
            String filePath = Paths.get(uploadDirectory, fileName).toString();
            log.info("FileName: " + file.getOriginalFilename());
            if (fileName == null || fileName.contains("..")) {
                model.addAttribute("invalid", "Sorry! Filename contains invalid path sequence \" + fileName");
                return new ResponseEntity<>("Sorry! Filename contains invalid path sequence " + fileName, HttpStatus.BAD_REQUEST);
            }
            try {
                File dir = new File(uploadDirectory);
                if (!dir.exists()) {
                    log.info("Folder Created");
                    dir.mkdirs();
                }
                // Save the file locally
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
                stream.write(file.getBytes());
                stream.close();
            } catch (Exception e) {
                log.info("in catch");
                e.printStackTrace();
            }
            byte[] imageData = file.getBytes();
            book.setPicByte(imageData);
            adminServices.addBookToStore(book);
            log.info("HttpStatus===" + new ResponseEntity<>(HttpStatus.OK));
            return new ResponseEntity<>("Product Saved With File - " + fileName, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Exception: " + e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/books/display/{id}")
    @ResponseBody
    void showImage(@PathVariable("id") Long id, HttpServletResponse response)
            throws ServletException, IOException {
        log.info("Id :: " + id);
        Book book = adminServices.getBookById(id);
//        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        response.getOutputStream().write(book.getPicByte());
        response.getOutputStream().close();
    }

    @GetMapping("/books/bookDetails/{id}")
    String showBookDetails(@PathVariable("id") Long id, Model model) {
        try {
            log.info("Id :: " + id);
            if (id != 0) {
                Book book = adminServices.getBookById(id);

                log.info("products :: " + book);
                if (book != null) {
                    model.addAttribute("book", book);
                    return "adminViewBookDetails";
                }
                return "redirect:/admin/books";
            }
            return "redirect:/admin/books";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/admin/books";
        }
    }

    @GetMapping("/books/edit/{id}")
    public String editBookDetails (@PathVariable(value = "id") long id, Model model) {
        model.addAttribute("book", adminServices.getBookById(id));
        return "adminEditBook";
    }




    @GetMapping("/books/{id}")
    public String getProductById(Model model, @PathVariable("id") long id) {

        model.addAttribute("book",adminServices.getBookById(id));
        return "adminViewBookDetails";
    }

//    @PutMapping("/books/update")
//    public void updateBook(@RequestBody Book book) {
//        adminServices.addBookToStore(book);
//    }

    @GetMapping("/books/delete/{id}" )
    public String deleteBook(@PathVariable("id") long id) {
//        Book book = adminServices.getBookById(id);
        adminServices.removeBookFromStore(id);
        return "redirect:/admin/books";
    }


    @GetMapping("/users")
    public ModelAndView viewAllUsers(){
        ModelAndView modelAndView = new ModelAndView("userInfo");
        List<User> listOfUsers = adminServices.getUsersByUserRole(UserType.CUSTOMER);
        modelAndView.addObject("listOfUsers", listOfUsers);
        return modelAndView;
    }

    @GetMapping("/user/delete/{id}" )
    public String deleteUserFromList(@PathVariable("id") long id) {
        adminServices.deleteUser(id);
        return "redirect:/admin/user";
    }

    @GetMapping("/showPurchase/{id}")
    public String showPurchaseHistory(@PathVariable(value = "id") long id, Model model, HttpSession session){
        List<Cart> list = cartServices.getPurchaseHistory(id);
        model.addAttribute("carts", list);
        return "adminViewUserPurchaseHistory";
    }

}
