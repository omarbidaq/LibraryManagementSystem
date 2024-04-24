package com.maidscc.lms.Service;

import com.maidscc.lms.Entity.Book;
import com.maidscc.lms.ExceptionHandlers.ResourceNotFoundException;
import com.maidscc.lms.Repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
public class BookService {
    private final BookRepository bookRepository;
    @Autowired
    public BookService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooks(){
        return bookRepository.findAll();
    }
    public Book getBookById(int id){
        return bookRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Book with ID " + id + " Not Found"));
    }
    @Transactional
    public Book addBook(Book book){
        if (book != null){
            return bookRepository.save(book);
        }else {
            throw new ResourceNotFoundException("You are Sending Empty fields");
        }

    }

    @Transactional
    public Book updateBook(Integer id, Book updatedBook){
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Existing Book with this ID: " + id));

        book.setId(id);
        book.setTitle(updatedBook.getTitle());
        book.setAuthor(updatedBook.getAuthor());
        book.setPublicationYear(updatedBook.getPublicationYear());
        book.setIsbn(updatedBook.getIsbn());

        return bookRepository.save(book);
    }
    @Transactional
    public void deleteBookById(int id){
        bookRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("No Existing Book with this ID: " + id));

        bookRepository.deleteById(id);
    }
}