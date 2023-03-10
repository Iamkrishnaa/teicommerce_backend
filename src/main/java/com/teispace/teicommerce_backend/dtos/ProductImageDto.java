package com.teispace.teicommerce_backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * A DTO for the {@link com.teispace.teicommerce_backend.models.ProductImage} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductImageDto implements Serializable {
    private Long id;
    private String image;
}