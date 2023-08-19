package com.example.reborn.type.entity;

import javax.persistence.*;

import com.example.reborn.type.etc.ReceiptStatus;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "donation_status")
@NoArgsConstructor
@Getter
public class DonationStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long donationStatusId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne
    @JoinColumn(name = "receipt_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Receipt receipt;

    @Enumerated(EnumType.STRING)
    private ReceiptStatus statusName;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = true)
    private LocalDateTime pickupAt;


    @Builder
    public DonationStatus(User user, Receipt receipt, ReceiptStatus statusName, LocalDateTime createdAt) {
        this.user = user;
        this.receipt = receipt;
        this.statusName = statusName;
        this.createdAt = createdAt;
    }


    public void setStatusName(ReceiptStatus status) {
        this.statusName = status;
    }

    public void setPickupAt(LocalDateTime pickupAt) {
        this.pickupAt = pickupAt;
    }
}

