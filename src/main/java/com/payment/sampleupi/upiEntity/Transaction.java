package com.payment.sampleupi.upiEntity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    private BigDecimal amount;

    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;
    @Column(name = "transaction_no", unique = true)
    private String transactionNumber;
    private String status;
    private String description;
    @PrePersist
    protected void onCreate() {
        transactionDate = LocalDateTime.now();
    }
}
