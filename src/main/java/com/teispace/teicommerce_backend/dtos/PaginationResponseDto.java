package com.teispace.teicommerce_backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class PaginationResponseDto {
    private int currentPage;
    private int currentPageSize;
    private int totalPages;
    private long totalCount;
    private Object data;
}
