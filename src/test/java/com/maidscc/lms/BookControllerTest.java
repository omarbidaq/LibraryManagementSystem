package com.maidscc.lms;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.reset;

import com.maidscc.lms.Configuration.SecurityConfig;
import com.maidscc.lms.Controller.BookController;
import com.maidscc.lms.Entity.Book;
import com.maidscc.lms.Service.BookService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@WebMvcTest(controllers = BookController.class, includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class))
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setId(1);
        book.setTitle("1984");
        book.setAuthor("George Orwell");
        book.setPublicationYear(1980);
        book.setIsbn("1234567890");
    }

    @AfterEach
    void tearDown() {
        reset(bookService);
    }

    @Test
    @WithMockUser(username="user",roles={"USER","ADMIN"})
    void getAllBooksTest() throws Exception {
        given(bookService.getBooks()).willReturn(List.of(book));
        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(book.getId()))
                .andExpect(jsonPath("$[0].title").value(book.getTitle()))
                .andExpect(jsonPath("$[0].author").value(book.getAuthor()))
                .andExpect(jsonPath("$[0].publicationYear").value(book.getPublicationYear()))
                .andExpect(jsonPath("$[0].isbn").value(book.getIsbn()));
    }

    @Test
    @WithMockUser(username="user",roles={"USER","ADMIN"})
    void getBookByIdTest() throws Exception {
        given(bookService.getBookById(1)).willReturn(book);
        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(book.getId()))
                .andExpect(jsonPath("$.title").value(book.getTitle()))
                .andExpect(jsonPath("$.author").value(book.getAuthor()))
                .andExpect(jsonPath("$.publicationYear").value(book.getPublicationYear()))
                .andExpect(jsonPath("$.isbn").value(book.getIsbn()));
    }

    @Test
    @WithMockUser(username="admin",roles={"ADMIN"})
    void createBookTest() throws Exception {
        given(bookService.addBook(any(Book.class))).willReturn(book);
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"1984\",\"author\":\"George Orwell\",\"publicationYear\":\"1980\",\"isbn\":\"1234567890\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("1984"))
                .andExpect(jsonPath("$.author").value("George Orwell"))
                .andExpect(jsonPath("$.publicationYear").value("1980"))
                .andExpect(jsonPath("$.isbn").value("1234567890"));
    }

    @Test
    @WithMockUser(username="admin",roles={"ADMIN"})
    void updateBookTest() throws Exception {
        given(bookService.updateBook(eq(1),any(Book.class))).willReturn(book);
        book.setTitle("Animal Farm");
        book.setAuthor("George Orwell");
        book.setPublicationYear(1980);
        book.setIsbn("1234567890");

        mockMvc.perform(put("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"1984\",\"author\":\"George Orwell\",\"publicationYear\":\"1980\",\"isbn\":\"1234567890\"}"))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.title").value("Animal Farm"))
                .andExpect(jsonPath("$.author").value("George Orwell"))
                .andExpect(jsonPath("$.publicationYear").value("1980"))
                .andExpect(jsonPath("$.isbn").value("1234567890"));
    }

    @Test
    @WithMockUser(username="admin",roles={"ADMIN"})
    void deleteBookTest() throws Exception {
        willDoNothing().given(bookService).deleteBookById(1);
        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isOk());
    }
}
