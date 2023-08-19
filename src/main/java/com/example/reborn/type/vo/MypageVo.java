package com.example.reborn.type.vo;

import com.example.reborn.type.dto.MypageDto;
import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
public class MypageVo {
//User
    private Long userId;
    private String nickname;
    private String profileImageUrl;
private String name;
    private Long point;
//Order
   private Long donationPoint;
   private Long donationCount;

    //Receipt


    public MypageVo(MypageDto dto,Long donationPoint,Long donationCount)
    {
        this.userId=dto.getUser().getUserId();
        this.nickname=dto.getUser().getNickname();
        this.name=dto.getUser().getName();
        this.point=dto.getUser().getPoint();
        this.donationPoint=donationPoint;
        this.donationCount=donationCount;

    }



}
