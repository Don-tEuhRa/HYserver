package com.example.reborn.type.vo;

import com.example.reborn.type.entity.*;
import com.example.reborn.type.etc.OrderStatus;
import com.example.reborn.type.etc.PaymentMethod;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;


@Getter
@ToString
public class OrderVo {

    private Long orderId;

    private OrderStatus status;

    private String phone; // 사용자 전화번호

    private Address address; // 배송지 아이디


    private Long myPoint; // 사용자 포인트

    private Long usePoint; // 사용 포인트

    private PaymentMethod paymentMethod; // 결제 방법

    private LocalDateTime paymentDate; // 결제 날짜

    public OrderVo(Order order,Address address) {

        this.phone = order.getUser().getPhone();
       // this.address =
        this.myPoint = order.getPoint();
        this.usePoint = order.getPoint();
        this.paymentMethod = order.getPaymentMethod();
        this.paymentDate = order.getCreated_at();
        this.orderId = order.getOrderId();
        this.status = order.getStatus();
        this.address=address;

    }





}