package com.example.reborn.type.vo;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class DonationCountVo {

    private long receiptCount;
    private long pickupCount;
    private long reformCount;
    private long arriveCount;
    private long productCount;
    private long donationCount;

    public DonationCountVo(long receiptCount, long pickupCount, long reformCount, long arriveCount, long productCount, long donationCount){
        this.receiptCount = receiptCount;
        this.pickupCount = pickupCount;
        this.reformCount = reformCount;
        this.arriveCount = arriveCount;
        this.productCount = productCount;
        this.donationCount = donationCount;
    }
}
