package com.example.reborn.type.dto;

import com.example.reborn.type.entity.DonationStatus;
import com.example.reborn.type.entity.Order;
import com.example.reborn.type.entity.Receipt;
import com.example.reborn.type.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MypageDto {

    private User user;


    private List<Order> order;

    private List<Receipt> receipt;

    private List<DonationStatus> donationStatus;

    private Long orderCount;

    private Long donationStatusCount;

    private Long receiptCount;

    @Builder
    public MypageDto(User user, List<Order> order, List<Receipt> receipt, List<DonationStatus> donationStatus, Long orderCount, Long donationStatusCount, Long receiptCount) {
        this.user = user;
        this.order = order;
        this.receipt = receipt;
        this.donationStatus = donationStatus;
        this.orderCount = orderCount;
        this.donationStatusCount = donationStatusCount;
        this.receiptCount = receiptCount;
    }





}
