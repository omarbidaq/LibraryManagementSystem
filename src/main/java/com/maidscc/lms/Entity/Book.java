package com.maidscc.lms.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotEmpty(message = "Title must Be filled")
    @Size(max = 255, message = "Name should not exceed 255 characters ")
    @Column(name = "title")
    private String title;

    @NotEmpty(message = "Author must filled")
    @Size(max = 255, message = "Name should not exceed 255 characters ")
    @Column(name = "author")
    private String author;

    @NotNull(message = "Publication Year must be filled")
    @Positive(message = "Publication Year must be Positive")
    @Column(name = "publication_year")
    private Integer publicationYear;

    @NotEmpty(message = "ISBN must be filled")
    @Size(max = 255, message = "Name should not exceed 255 characters ")
    @Column(name = "isbn")
    private String isbn;
}