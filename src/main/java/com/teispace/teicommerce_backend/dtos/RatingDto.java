package com.teispace.teicommerce_backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * A DTO for the {@link com.teispace.teicommerce_backend.models.Rating} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingDto implements Serializable {
    private Long id;
    private ProductDto product;
    private UserDto user;
    private double rating;
    private String comment;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}