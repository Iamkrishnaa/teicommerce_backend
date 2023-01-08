package com.teispace.teicommerce_backend.controllers;

import com.teispace.teicommerce_backend.dtos.PaginationResponseDto;
import com.teispace.teicommerce_backend.serviceImplementations.ProductServiceImplementation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
