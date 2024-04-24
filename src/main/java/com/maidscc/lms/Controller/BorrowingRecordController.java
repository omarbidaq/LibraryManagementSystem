package com.maidscc.lms.Controller;

import com.maidscc.lms.Entity.BorrowingRecord;
import com.maidscc.lms.Service.BorrowingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BorrowingRecordController {
    private final BorrowingRecordService borrowingRecordService;
    @Autowired
    private BorrowingRecordController(BorrowingRecordService borrowingRecordService){
        this.borrowingRecordService = borrowingRecordService;
    }

    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public BorrowingRecord setBorrow(@PathVariable Integer bookId, @PathVariable Integer patronId){
        return borrowingRecordService.borrow(bookId,patronId);
    }

    @PutMapping("/return/{bookId}/patron/{patronId}")
    public BorrowingRecord returnBorrow(@PathVariable Integer bookId,@PathVariable Integer patronId){
        return borrowingRecordService.setReturnDate(bookId,patronId);
    }

}
