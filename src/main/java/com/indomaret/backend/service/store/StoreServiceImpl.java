package com.indomaret.backend.service.store;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import com.indomaret.backend.dto.StoreDTO;
import com.indomaret.backend.model.Branch;
import com.indomaret.backend.model.Store;
import com.indomaret.backend.model.WhitelistStore;
import com.indomaret.backend.payload.GenericResponse;
import com.indomaret.backend.repository.BranchRepository;
import com.indomaret.backend.repository.StoreRepository;
import com.indomaret.backend.repository.WhitelistStoreRepository;


@Service
public class StoreServiceImpl implements StoreService {
    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private WhitelistStoreRepository whitelistRepository;


    private StoreDTO mapToDTO(Store store) {
        StoreDTO dto = new StoreDTO();
        dto.setId(store.getId());
        dto.setName(store.getName());
        dto.setAddress(store.getAddress());
        dto.setActive(store.isActive());
        dto.setBranchId(store.getBranch().getId());
        dto.setCreatedAt(store.getCreatedAt());
        dto.setUpdatedAt(store.getUpdatedAt());
        return dto;
    }

    @Override
    public Page<Store> searchStoresByProvince(String provinceName, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Store> byProvince = storeRepository.findByProvinceName(provinceName, pageable);

        // get all whitelist stores (active)
        List<WhitelistStore> whitelist = whitelistRepository.findAll();
        List<Store> whitelistStores = whitelist.stream()
                .map(WhitelistStore::getStore)
                .filter(s -> s.isActive() && !s.isDeleted())
                .collect(Collectors.toList());

        // Merge while avoiding duplicates (by id)
        Map<Long, Store> merged = new LinkedHashMap<>();
        byProvince.forEach(s -> merged.put(s.getId(), s));
        for (Store ws : whitelistStores) merged.putIfAbsent(ws.getId(), ws);

        List<Store> mergedList = new ArrayList<>(merged.values());
        int start = Math.min((int) pageable.getOffset(), mergedList.size());
        int end = Math.min(start + pageable.getPageSize(), mergedList.size());

        List<Store> pageContent = mergedList.subList(start, end);
        return new PageImpl<>(pageContent, pageable, mergedList.size());
    }

    @Override
    public Page<Store> getAllStores(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return storeRepository.findAllActive(pageable);
    }

    @Override
    public Store addToWhitelist(Long storeId) {
        // check store exists and active
        Store s = storeRepository.findById(storeId).orElseThrow(() -> new RuntimeException("Store not found"));
        if (!s.isActive() || s.isDeleted()) throw new RuntimeException("Store not active");

        if (whitelistRepository.findByStoreId(storeId).isPresent()) {
            throw new RuntimeException("Store already whitelisted");
        }
        WhitelistStore w = WhitelistStore.builder().store(s).build();
        return whitelistRepository.save(w).getStore();
    }

    @Override
    public void removeFromWhitelist(Long storeId) {
        Optional<WhitelistStore> opt = whitelistRepository.findByStoreId(storeId);
        if (opt.isPresent()) {
            whitelistRepository.delete(opt.get());
        } else {
            throw new RuntimeException("Whitelist entry not found");
        }
    }

    @Override
    public GenericResponse<StoreDTO> createStore(StoreDTO dto) {
        Optional<Branch> branchOpt = branchRepository.findById(dto.getBranchId());
        if (branchOpt.isEmpty()) {
            return GenericResponse.<StoreDTO>builder()
                    .success(false)
                    .message("Branch not found")
                    .data(null)
                    .build();
        }

        Store store = Store.builder()
        .name(dto.getName())
        .address(dto.getAddress())
        .active(dto.isActive())
        .branch(branchOpt.get())
        .createdAt(LocalDateTime.now())
        .updatedAt(LocalDateTime.now())
        .build();

        storeRepository.save(store);

        return GenericResponse.<StoreDTO>builder()
                .success(true)
                .message("Store created successfully")
                .data(mapToDTO(store))
                .build();
    }

    @Override
    public GenericResponse<StoreDTO> updateStore(Long id, StoreDTO dto) {
        Optional<Store> storeOpt = storeRepository.findById(id);
        if (storeOpt.isEmpty()) {
            return GenericResponse.<StoreDTO>builder()
                    .success(false)
                    .message("Store not found")
                    .data(null)
                    .build();
        }

        Store store = storeOpt.get();
        store.setName(dto.getName());
        store.setAddress(dto.getAddress());
        store.setActive(dto.isActive());
        store.setUpdatedAt(LocalDateTime.now());

        storeRepository.save(store);

        return GenericResponse.<StoreDTO>builder()
                .success(true)
                .message("Store updated successfully")
                .data(mapToDTO(store))
                .build();
    }

    @Override
    public GenericResponse<String> deleteStore(Long id) {
        Optional<Store> storeOpt = storeRepository.findById(id);
        if (storeOpt.isEmpty()) {
            return GenericResponse.<String>builder()
                    .success(false)
                    .message("Store not found")
                    .data(null)
                    .build();
        }

        Store store = storeOpt.get();
        store.setDeleted(true);
        storeRepository.save(store);

        return GenericResponse.<String>builder()
                .success(true)
                .message("Store deleted successfully")
                .data("Deleted ID: " + id)
                .build();
    }
    
}
