package com.maidscc.lms.Repository;

import com.maidscc.lms.Entity.Patron;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatronRepository extends JpaRepository<Patron,Integer> {
}
