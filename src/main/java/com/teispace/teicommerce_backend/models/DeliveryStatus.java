package com.teispace.teicommerce_backend.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;


enum DeliveryStatusType {
    PENDING,
    IN_TRANSIT,
    DELIVERED,
    CANCELLED,

}

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "delivery_status")
public class DeliveryStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id")
    private Long id;

    @JoinColumn(name = "order_id", nullable = false)
    @ManyToOne(cascade = CascadeType.ALL)
    private Order order;

    @Column(nullable = false, columnDefinition = "ENUM('PENDING', 'IN_TRANSIT', 'DELIVERED', 'CANCELLED')")
    @Enumerated
    private DeliveryStatusType status;

    private String description;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;
}