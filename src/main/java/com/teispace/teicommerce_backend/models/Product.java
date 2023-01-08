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
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id")
    private Long id;
    @Column(nullable = false)
    private String title;

    private String description;
    private double price;
    private int quantity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "product",
            fetch = FetchType.LAZY
    )
    private Set<Rating> ratings;

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "product",
            fetch = FetchType.LAZY
    )

    private Set<ProductImage> productImages;

    @Transient
    private double ratingValue;

    @Transient
    private int totalRatings;

    //get rating value and total ratings
    public double getRatingValue() {
        return ratingValue;
    }

    //set rating value and total ratings
    public void setRatingValue() {
        for (Rating rating : ratings) {
            this.ratingValue += rating.getRating();
        }
        this.ratingValue = this.ratingValue / ratings.size();
    }

    public int getTotalRatings() {
        return totalRatings;
    }

    public void setTotalRatings() {
        this.totalRatings = ratings.size();
    }
}
