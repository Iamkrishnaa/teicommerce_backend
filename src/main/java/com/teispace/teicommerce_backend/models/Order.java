package com.teispace.teicommerce_backend.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.Set;


enum OrderStatus {
    PENDING, PLACED, CONFIRMED, SHIPPED, DELIVERED, CANCELLED
}

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @Column(
            nullable = false,
            columnDefinition = "enum('PENDING', 'PLACED', 'CONFIRMED', 'SHIPPED', 'DELIVERED', 'CANCELLED')"
    )
    @Enumerated
    private OrderStatus status;
    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "order",
            fetch = FetchType.LAZY
    )
    private Set<OrderItem> orderItems;

    @OneToOne(
            cascade = CascadeType.ALL,
            mappedBy = "order"
    )
    private Payment payment;

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "order",
            fetch = FetchType.LAZY
    )
    private Set<DeliveryStatus> deliveryStatus;
}