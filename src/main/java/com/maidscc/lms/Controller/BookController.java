package com.maidscc.lms.Controller;

import com.maidscc.lms.Entity.Book;
import com.maidscc.lms.Service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@Slf4j
@Tag(name = "Book API", description = "Book management API")
@SecurityRequirement(name = "basicAuth")
public class BookController {
    private final BookService bookService;
    @Autowired
    public BookController(BookService bookService){this.bookService = bookService;}

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retrieve All Books", description = "Retrieve a list of books")
    @ApiResponse(responseCode = "200",description = "Successfully retrieve list of books")
    public List<Book> getAllBooks(){
        return bookService.getBooks();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get a book by ID",description = "Retrive a book with specific ID")
    @ApiResponse(responseCode = "200",description = "Successfully retrieved the book")
    @ApiResponse(responseCode = "404",description = "Book Not Found")
    public Book getBookById(@PathVariable Integer id){
        return bookService.getBookById(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new Book")
    @ApiResponse(responseCode = "201",description ="Successfully create a new book")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Book to be added",required = true)
    public Book addBook(@RequestBody Book book){
        return bookService.addBook(book);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "update a book", description = "Update an existing book's details")
    @ApiResponse(responseCode = "200",description = "book updated successfully")
    @ApiResponse(responseCode = "404",description = "Not Found")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "updated book details",required = true)
    public Book updateBook(@PathVariable Integer id,@RequestBody Book book){
        return bookService.updateBook(id,book);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "delete a book", description = "delete a book with a specified ID")
    @ApiResponse(responseCode = "200",description = "book deleted successfully")
    @ApiResponse(responseCode = "404",description = "Not Found")
    public void deleteBookById(@PathVariable Integer id){
        bookService.deleteBookById(id);
    }

}