package com.teispace.teicommerce_backend.models;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id")
    private Long id;
    @Column(nullable = false)
    private String title;
    private String description;
    private String image;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "category",
            fetch = FetchType.LAZY
    )
    private Set<Product> products;
}
