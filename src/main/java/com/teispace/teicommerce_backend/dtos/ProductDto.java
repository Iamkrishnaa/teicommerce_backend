package com.teispace.teicommerce_backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

/**
 * A DTO for the {@link com.teispace.teicommerce_backend.models.Product} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto implements Serializable {
    private Long id;
    private String title;
    private String description;
    private double price;
    private int quantity;
    private CategoryDto category;
    private double averageRating;
    private int totalRatings;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Set<ProductImageDto> productImages;


}