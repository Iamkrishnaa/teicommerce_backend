package com.teispace.teicommerce_backend.services;

import com.teispace.teicommerce_backend.dtos.PaginationResponseDto;

public interface ProductService {
    PaginationResponseDto getAvailableProducts(
            int pageNumber,
            int pageSize,
            String sortBy
    );

    PaginationResponseDto getProducts(
            int pageNumber,
            int pageSize,
            String sortBy
    );

    PaginationResponseDto getTrendingProduct(
            int pageNumber,
            int pageSize,
            String sortBy
    );

    PaginationResponseDto getProductByCategory(
            int pageNumber,
            int pageSize,
            String sortBy,
            String category
    );
}
