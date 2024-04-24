package com.maidscc.lms.Repository;

import com.maidscc.lms.Entity.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface BorrowingRecordsRepository extends JpaRepository<BorrowingRecord,Integer> {

    @Query("SELECT r FROM BorrowingRecord r WHERE r.book.id = :bookId AND r.patron.id = :patronId AND r.returnDate IS NULL")
    Optional<BorrowingRecord> findActiveByBookIdAndPatronId(Integer bookId, Integer patronId);
}
