package com.teispace.teicommerce_backend.repos;

import com.teispace.teicommerce_backend.models.Category;
import com.teispace.teicommerce_backend.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAllByQuantityGreaterThan(int quantity, Pageable pageable);

    Page<Product> findAllByCategory(Category category, Pageable pageable);
}