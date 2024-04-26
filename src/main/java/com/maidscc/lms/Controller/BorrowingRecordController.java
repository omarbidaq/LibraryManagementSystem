package com.maidscc.lms.Controller;

import com.maidscc.lms.Entity.BorrowingRecord;
import com.maidscc.lms.Service.BorrowingRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Tag(name = "Borrowing Records API")
@SecurityRequirement(name = "basicAuth")
public class BorrowingRecordController {
    private final BorrowingRecordService borrowingRecordService;
    @Autowired
    private BorrowingRecordController(BorrowingRecordService borrowingRecordService){
        this.borrowingRecordService = borrowingRecordService;
    }

    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Allow a patron to borrow a book", description = "Allow a patron to borrow a book")
    @ApiResponse(responseCode = "201",description = "The Patron has a borrowed a book successfully")
    public BorrowingRecord setBorrow(@PathVariable Integer bookId, @PathVariable Integer patronId){
        return borrowingRecordService.borrow(bookId,patronId);
    }

    @PutMapping("/return/{bookId}/patron/{patronId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Record the return of a book",description ="Record the return date of a borrowed book" )
    @ApiResponse(responseCode = "200",description = "The Patron has returned the book")
    public BorrowingRecord returnBorrow(@PathVariable Integer bookId,@PathVariable Integer patronId){
        return borrowingRecordService.setReturnDate(bookId,patronId);
    }

}