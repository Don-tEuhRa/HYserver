package com.example.reborn.type.vo;

import com.example.reborn.type.entity.Address;
import com.example.reborn.type.entity.Order;
import com.example.reborn.type.entity.OrderProductRelation;
import com.example.reborn.type.etc.OrderStatus;
import com.example.reborn.type.etc.PaymentMethod;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;


@Getter
@ToString
public class OrderProductVo {

    private Long orderId;

    private OrderStatus status;

    private String phone; // 사용자 전화번호

    private Address address; // 배송지 아이디


    private PaymentMethod paymentMethod;

    private LocalDateTime paymentDate; // 결제 날짜


    private String thumbnailUrl;

    private String title;

    private Long price;

    public OrderProductVo(OrderProductRelation order, Address address) {

        this.phone = order.getOrder().getUser().getPhone();
        this.paymentMethod = order.getOrder().getPaymentMethod();
        this.paymentDate = order.getOrder().getCreated_at();
        this.orderId = order.getOrder().getOrderId();
        this.status = order.getOrder().getStatus();
        this.address=address;
        this.thumbnailUrl=order.getProduct().getThumbnailUrl();
        this.title=order.getProduct().getTitle();
        this.price=order.getProduct().getPrice();

    }


}
