package com.indomaret.backend.dto;

import java.time.LocalDateTime;

import lombok.*;

@Data
@Builder
public class BranchDTO {
    private Long id;
    private String name;
    private Long provinceId;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
