package com.indomaret.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.indomaret.backend.model.Branch;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {
   @Query("SELECT b FROM Branch b WHERE b.active = true AND b.deleted = false ORDER BY b.id ASC")
    List<Branch> findAllActive();
}
