package com.indomaret.backend.repository;

import java.util.List;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.indomaret.backend.model.Store;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    @Query("SELECT s FROM Store s WHERE s.active = true AND s.deleted = false ORDER BY s.id ASC") 
    Page<Store> findAllActive(Pageable pageable);

    @Query("SELECT s FROM Store s JOIN s.branch b JOIN b.province p " +
           "WHERE lower(p.name) LIKE lower(concat('%', :provinceName, '%')) " +
           "AND s.active = true AND s.deleted = false")
    Page<Store> findByProvinceName(String provinceName, Pageable pageable);

     @Query("SELECT s FROM Store s JOIN WhitelistStore w ON w.store.id = s.id " +
           "WHERE s.active = true AND s.deleted = false")
    List<Store> findWhitelistStores();
}
