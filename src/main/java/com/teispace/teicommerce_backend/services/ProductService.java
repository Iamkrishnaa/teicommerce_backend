package com.teispace.teicommerce_backend.services;

import com.teispace.teicommerce_backend.dtos.PaginationResponseDto;
import com.teispace.teicommerce_backend.dtos.ProductDto;

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

    ProductDto getProductById(Long id);

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
