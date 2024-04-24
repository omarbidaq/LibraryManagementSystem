package com.maidscc.lms.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "borrowing_records")
public class BorrowingRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull(message = "Missing Book ID")
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @NotNull(message = "Missing Patron ID")
    @ManyToOne
    @JoinColumn(name = "patron_id")
    private Patron patron;

    @PastOrPresent(message = "Invalid Date")
    @Column(name = "borrow_date")
    private LocalDate borrowDate;

    @PastOrPresent(message = "Invalid Date")
    @Column(name = "return_date")
    private LocalDate returnDate;
}
