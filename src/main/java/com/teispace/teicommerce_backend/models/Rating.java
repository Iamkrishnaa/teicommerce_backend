package com.teispace.teicommerce_backend.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "ratings")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id")
    private Long id;

    @JoinColumn(name = "product_id", nullable = false)
    @ManyToOne(cascade = CascadeType.ALL)
    private Product product;

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    @Column(nullable = false)
    private double rating;

    @Column(nullable = false)
    private String comment;


    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;
}
