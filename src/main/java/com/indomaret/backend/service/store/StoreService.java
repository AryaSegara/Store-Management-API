package com.indomaret.backend.service.store;

import org.springframework.data.domain.Page;

import com.indomaret.backend.dto.StoreDTO;
import com.indomaret.backend.model.Store;
import com.indomaret.backend.payload.GenericResponse;


public interface StoreService {

    Page<Store> getAllStores(int page, int size);
    Page<Store> searchStoresByProvince(String provinceName, int page, int size);
    Store addToWhitelist(Long storeId);
    void removeFromWhitelist(Long storeId);
    GenericResponse<StoreDTO> createStore(StoreDTO dto);
    GenericResponse<StoreDTO> updateStore(Long id, StoreDTO dto);
    GenericResponse<String> deleteStore(Long id);
    
}
