package com.indomaret.backend.dto;

import java.util.List;

import lombok.*;

@Data
@Builder
public class PaginatedResponseDTO<T> {
    private List<T> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
}
