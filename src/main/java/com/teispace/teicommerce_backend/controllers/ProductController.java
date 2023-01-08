package com.teispace.teicommerce_backend.controllers;

import com.teispace.teicommerce_backend.dtos.PaginationResponseDto;
import com.teispace.teicommerce_backend.serviceImplementations.ProductServiceImplementation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductServiceImplementation productServiceImplementation;

    public ProductController(
            ProductServiceImplementation productServiceImplementation
    ) {
        this.productServiceImplementation = productServiceImplementation;
    }

    @GetMapping("/all")
    public ResponseEntity<PaginationResponseDto> getAllProducts(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "20", required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy
    ) {
        PaginationResponseDto products = productServiceImplementation.getProducts(pageNumber, pageSize, sortBy);

        return ResponseEntity.ok(products);
    }

    @GetMapping("/categories/{category}")
    public ResponseEntity<PaginationResponseDto> getAllProductsByCategory(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "20", required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @PathVariable String category
    ) {
        PaginationResponseDto products = productServiceImplementation.getProductByCategory(pageNumber, pageSize, sortBy, category);

        return ResponseEntity.ok(products);
    }

    @GetMapping("/available")
    public ResponseEntity<PaginationResponseDto> getAvailableProducts(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "20", required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy
    ) {
        PaginationResponseDto products = productServiceImplementation.getAvailableProducts(pageNumber, pageSize, sortBy);

        return ResponseEntity.ok(products);
    }

    @GetMapping("/trending")
    public ResponseEntity<PaginationResponseDto> getTrendingProducts(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "20", required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy
    ) {
        PaginationResponseDto products = productServiceImplementation.getTrendingProduct(pageNumber, pageSize, sortBy);

        return ResponseEntity.ok(products);
    }
}
