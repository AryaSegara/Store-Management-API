package com.indomaret.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.indomaret.backend.model.Province;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Long> {
    @Query("SELECT p FROM Province p WHERE p.active = true AND p.deleted = false ORDER BY p.id ASC")
    List<Province> findAllActive();
}

