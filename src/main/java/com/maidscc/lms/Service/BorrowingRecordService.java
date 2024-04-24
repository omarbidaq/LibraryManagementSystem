package com.maidscc.lms.Service;

import com.maidscc.lms.Entity.Book;
import com.maidscc.lms.Entity.BorrowingRecord;
import com.maidscc.lms.Entity.Patron;
import com.maidscc.lms.ExceptionHandlers.ResourceNotFoundException;
import com.maidscc.lms.Repository.BookRepository;
import com.maidscc.lms.Repository.BorrowingRecordsRepository;
import com.maidscc.lms.Repository.PatronRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Slf4j
public class BorrowingRecordService {
    private final BorrowingRecordsRepository borrowingRecordsRepository;
    private final BookRepository bookRepository;
    private final PatronRepository patronRepository;
    @Autowired
    public BorrowingRecordService(BorrowingRecordsRepository borrowingRecordsRepository,BookRepository bookRepository
            ,PatronRepository patronRepository){
        this.borrowingRecordsRepository = borrowingRecordsRepository;
        this.bookRepository = bookRepository;
        this.patronRepository = patronRepository;
    }

    @Transactional
    public BorrowingRecord borrow(Integer bookId,Integer patronId){
        Book borrowedBook = bookRepository.findById(bookId)
                .orElseThrow(()-> new ResourceNotFoundException("No Existing Book With ID: " + bookId));

        Patron borrower = patronRepository.findById(patronId)
                .orElseThrow(()-> new ResourceNotFoundException("No Existing Patron with ID : " + patronId));

        Optional<BorrowingRecord> record = borrowingRecordsRepository.findActiveByBookIdAndPatronId(bookId,patronId);
        if (record.isPresent()){
            log.info("Book is already borrowed");
            throw new IllegalStateException("Book is already borrowed");
        }
        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(borrowedBook);
        borrowingRecord.setPatron(borrower);
        borrowingRecord.setBorrowDate(LocalDate.now());

        log.info("Book borrowed successfully with book Id {} and patron ID {}",bookId,patronId);
        return borrowingRecordsRepository.save(borrowingRecord);
    }

    @Transactional
    public BorrowingRecord setReturnDate(Integer bookId, Integer patronId){
        bookRepository.findById(bookId)
                .orElseThrow(()-> new ResourceNotFoundException("No Existing Book With ID " + bookId));
        patronRepository.findById(patronId)
                .orElseThrow(()-> new ResourceNotFoundException("No Existing Patron With ID " + patronId));

        Optional<BorrowingRecord> optionalRecord = borrowingRecordsRepository
                .findActiveByBookIdAndPatronId(bookId,patronId);

        if (optionalRecord.isEmpty()){
            throw new IllegalStateException("No active borrowing record found");
        }

        BorrowingRecord record = new BorrowingRecord();
        record.setId(optionalRecord.get().getId());
        record.setBook(optionalRecord.get().getBook());
        record.setPatron(optionalRecord.get().getPatron());
        record.setBorrowDate(optionalRecord.get().getBorrowDate());
        record.setReturnDate(LocalDate.now());

        return borrowingRecordsRepository.save(record);
    }
}