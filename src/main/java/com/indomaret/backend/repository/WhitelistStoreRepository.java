package com.indomaret.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.indomaret.backend.model.WhitelistStore;

@Repository
public interface WhitelistStoreRepository extends JpaRepository<WhitelistStore, Long> {
     Optional<WhitelistStore> findByStoreId(Long storeId);}
