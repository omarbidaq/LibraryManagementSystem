package com.maidscc.lms.Controller;

import com.maidscc.lms.Entity.Book;
import com.maidscc.lms.Service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@Slf4j
public class BookController {
    private final BookService bookService;
    @Autowired
    public BookController(BookService bookService){this.bookService = bookService;}

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Book> getAllBooks(){
        return bookService.getBooks();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Book getBookById(@PathVariable Integer id){
        return bookService.getBookById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Book addBook(@RequestBody Book book){
        return bookService.addBook(book);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Book updateBook(@PathVariable Integer id,@RequestBody Book book){
        return bookService.updateBook(id,book);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBookById(@PathVariable Integer id){
        bookService.deleteBookById(id);
    }

}