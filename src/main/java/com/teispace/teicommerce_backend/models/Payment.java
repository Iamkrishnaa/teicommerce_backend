package com.teispace.teicommerce_backend.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;


enum PaymentStatus {
    PENDING,
    SUCCESS,
    FAILED,
    CANCELLED,
    REFUNDED,
}

enum PaymentMethod {
    CASH_ON_DELIVERY,
    KHALTI,
    ESEWA,
}

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id")
    private Long id;

    @JoinColumn(name = "order_id", nullable = false, unique = true)
    @OneToOne(cascade = CascadeType.ALL)
    private Order order;

    @Column(nullable = false, columnDefinition = "ENUM('PENDING', 'SUCCESS', 'FAILED', 'CANCELLED', 'REFUNDED')")
    @Enumerated
    private PaymentMethod paymentMethod;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false, columnDefinition = "ENUM('CASH_ON_DELIVERY', 'KHALTI', 'ESEWA')")
    @Enumerated
    private PaymentStatus paymentStatus;


    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;
}
