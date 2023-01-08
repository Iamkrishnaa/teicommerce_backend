package com.teispace.teicommerce_backend.repos;

import com.teispace.teicommerce_backend.models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    @Query("SELECT AVG(rating) FROM Rating WHERE product.id = ?1")
    Double findAverageRatingByProductId(Long productId);
}