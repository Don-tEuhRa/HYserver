package com.example.reborn.type.vo;

import com.example.reborn.type.entity.DonationStatus;
import com.example.reborn.type.etc.ReceiptStatus;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ProgressVo {

    private Long donationStatusId;
    private Long count;
    private ReceiptStatus donationStatus;


    public ProgressVo(DonationStatus donationStatus, ReceiptStatus statusName,Long count)
    {
        this.donationStatusId = donationStatus.getDonationStatusId();
        this.count=count;
        this.donationStatus = statusName;
    }
}
