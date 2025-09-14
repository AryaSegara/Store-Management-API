package com.indomaret.backend.controller;

// import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.indomaret.backend.dto.PaginatedResponseDTO;
import com.indomaret.backend.dto.StoreDTO;
import com.indomaret.backend.model.Store;
import com.indomaret.backend.payload.GenericResponse;
import com.indomaret.backend.service.store.StoreService;


@RestController
@RequestMapping("/api/stores")
public class StoreController {
    @Autowired
    private StoreService storeService;

    private StoreDTO mapToDTO(Store store) {
        StoreDTO dto = new StoreDTO();
        dto.setId(store.getId());
        dto.setName(store.getName());
        dto.setActive(store.isActive());
        if (store.getBranch() != null) {
            dto.setBranchId(store.getBranch().getId());
        }
        dto.setCreatedAt(store.getCreatedAt());
        dto.setUpdatedAt(store.getUpdatedAt());
        return dto;
    }

    @GetMapping("/search")
    public ResponseEntity<PaginatedResponseDTO<StoreDTO>> searchStores(
            @RequestParam String provinceName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Store> storePage = storeService.searchStoresByProvince(provinceName, page, size);

        Page<StoreDTO> storeDtoPage = storePage.map(this::mapToDTO);

        PaginatedResponseDTO<StoreDTO> response = PaginatedResponseDTO.<StoreDTO>builder()
                .content(storeDtoPage.getContent())
                .pageNumber(storeDtoPage.getNumber())
                .pageSize(storeDtoPage.getSize())
                .totalElements(storeDtoPage.getTotalElements())
                .totalPages(storeDtoPage.getTotalPages())
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<PaginatedResponseDTO<StoreDTO>> getAllStores(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Page<Store> storePage = storeService.getAllStores(page, size);

        Page<StoreDTO> storeDtoPage = storePage.map(this::mapToDTO);

        PaginatedResponseDTO<StoreDTO> response = PaginatedResponseDTO.<StoreDTO>builder()
                .content(storeDtoPage.getContent())
                .pageNumber(storeDtoPage.getNumber())
                .pageSize(storeDtoPage.getSize())
                .totalElements(storeDtoPage.getTotalElements())
                .totalPages(storeDtoPage.getTotalPages())
                .build();
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/whitelist/{storeId}")
    public ResponseEntity<?> addWhitelist(@PathVariable Long storeId) {
        return ResponseEntity.ok(storeService.addToWhitelist(storeId));
    }


    @DeleteMapping("/whitelist/{storeId}")
    public ResponseEntity<?> removeWhitelist(@PathVariable Long storeId) {
        storeService.removeFromWhitelist(storeId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public GenericResponse<StoreDTO> createStore(@RequestBody StoreDTO dto) {
        return storeService.createStore(dto);
    }

    @PutMapping("/{id}")
    public GenericResponse<StoreDTO> updateStore(@PathVariable Long id, @RequestBody StoreDTO dto) {
        return storeService.updateStore(id, dto);
    }

    @DeleteMapping("/{id}")
    public GenericResponse<String> deleteStore(@PathVariable Long id) {
        return storeService.deleteStore(id);
    }
}

