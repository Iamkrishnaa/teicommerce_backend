package com.teispace.teicommerce_backend.services;

import com.teispace.teicommerce_backend.dtos.PaginationResponseDto;

public interface CategoryService {
    PaginationResponseDto getCategories(Integer pageNumber, Integer pageSize, String sortBy);
}
