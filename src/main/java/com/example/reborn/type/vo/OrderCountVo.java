package com.example.reborn.type.vo;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class OrderCountVo {

    Long allOrderCount;
    Long payCount;
    Long deliveryCount;
    Long deliveredCount;
    Long completeCount;

    public OrderCountVo(Long allOrderCount, Long payCount, Long deliveryCount, Long deliveredCount, Long completeCount) {
        this.allOrderCount = allOrderCount;
        this.payCount = payCount;
        this.deliveryCount = deliveryCount;
        this.deliveredCount = deliveredCount;
        this.completeCount = completeCount;
    }
}
