package com.teispace.teicommerce_backend.services;

import com.teispace.teicommerce_backend.dtos.PaginationResponseDto;
import com.teispace.teicommerce_backend.dtos.ProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> getAvailableProducts(
            int pageNumber,
            int pageSize,
            String sortBy
    );

    PaginationResponseDto getProducts(
            int pageNumber,
            int pageSize,
            String sortBy
    );

    List<ProductDto> getTrendingProduct(
            int pageNumber,
            int pageSize,
            String sortBy
    );

    List<ProductDto> getProductByCategory(
            int pageNumber,
            int pageSize,
            String sortBy,
            String category
    );
}
