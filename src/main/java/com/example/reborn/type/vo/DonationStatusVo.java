package com.example.reborn.type.vo;

import com.example.reborn.type.entity.DonationStatus;
import com.example.reborn.type.entity.Product;
import com.example.reborn.type.etc.ReceiptStatus;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class DonationStatusVo {
//상단바

private ReceiptStatus receiptStatus;
    //기부 상태
    private String name;
    private String address;
    private String phoneNumber;
    private LocalDateTime pickUpDate; //수거날짜

    private long productId;
    private String productName;
    private long price;
    private LocalDateTime date; // 등록날짜


    public DonationStatusVo(DonationStatus donationStatus) {
        this.receiptStatus = donationStatus.getStatusName();
        this.name = donationStatus.getReceipt().getName();
        this.address = donationStatus.getReceipt().getAddress();
        this.phoneNumber =donationStatus.getReceipt().getPhoneNumber();
        this.pickUpDate = donationStatus.getPickupAt();

    }

    public DonationStatusVo(DonationStatus donationStatus, Product product) {
        this.receiptStatus = donationStatus.getStatusName();
        this.productId = product.getProductId();
        this.name = donationStatus.getReceipt().getName();
        this.productName = product.getTitle();
        this.price = product.getPrice();
        this.date = product.getCreatedAt();
        this.phoneNumber =donationStatus.getReceipt().getPhoneNumber();
        this.address = donationStatus.getReceipt().getAddress();
    }



}
