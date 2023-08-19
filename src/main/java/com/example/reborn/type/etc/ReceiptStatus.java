package com.example.reborn.type.etc;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReceiptStatus {
    //각enum의 description은 해당 enum의 한글 설명이고 값은 진행도의 따라 1씩 증가한다.
    //ex) 접수대기는 1, 접수완료는 2, 수거대기는 3, 수거중은 4, 수거완료는 5, 배달중은 6, 배달완료는 7, 접수취소는 8, 검수중은 9, 검수완료는 10, 리폼중은 11, 판매중은 12, 판매완료는 13, 판매취소는 14, 기부완료는 15


    접수대기(-1L,"접수대기")// 기부 신청이 접수된 상태(기부자가 기부 신청을 하고 기부자가 기부 신청을 완료한 상태
    ,접수완료(1L,"접수완료")// 기부 신청이 승인된 상태
    ,수거중(3L,"수거중")
    ,수거완료(4L,"수거완료")
    ,배달중(5L,"배달중")
    ,배달완료(6L,"배달완료") // 물품이 업사이클 센터에 전달된 상태
    ,검수중(7L,"검수중")
    ,검수완료(8L,"검수완료") // 물품이 업사이클링 되었는지 검수하는 상태(검수자가 검수를 완료한 상태)
    ,리폼중(9L,"리사이클링중")
    ,리폼완료(10L,"리폼완료")// 물품이 업사이클링 되고 있는 상태
    ,판매중(11L,"판매중") // 물품이 판매중인 상태(판매자가 판매를 시작한 상태
    ,판매완료(12L,"판매완료") // 물품이 판매된 상태(구매자가 물품을 구매한 상태
    ,기부완료(13L,"기부완료") // 물품이 기부된 상태(기부자가 물품을 기부한 상태
    ,판매취소(15L,"판매취소") // 판매자가 판매를 도중에 취소한 상태
    ,접수취소(17L,"접수취소"); // 기부자가 기부를 도중에 취소한 상태
    private Long value;
    private String description;


    public static ReceiptStatus getReceiptStatus(Long value) {
        for (ReceiptStatus receiptStatus : ReceiptStatus.values()) {
            if (receiptStatus.getValue().equals(value)) {
                return receiptStatus;
            }
        }
        return null;
    }
}
