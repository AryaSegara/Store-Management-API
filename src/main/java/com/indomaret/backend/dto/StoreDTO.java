package com.indomaret.backend.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class StoreDTO {
    private Long id;
    private String name;
    private String address;
    private boolean active;
    private Long branchId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
