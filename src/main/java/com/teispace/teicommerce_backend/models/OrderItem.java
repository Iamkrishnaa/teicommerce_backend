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
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id")
    private Long id;


    @JoinColumn(name = "order_id", nullable = false)
    @ManyToOne(cascade = CascadeType.ALL)
    private Order order;

    @JoinColumn(name = "product_id", nullable = false)
    @ManyToOne(cascade = CascadeType.ALL)
    private Product product;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private double price;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;
}
