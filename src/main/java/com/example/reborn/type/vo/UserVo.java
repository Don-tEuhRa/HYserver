package com.example.reborn.type.vo;

import com.example.reborn.type.entity.Address;
import com.example.reborn.type.entity.Interest;
import com.example.reborn.type.dto.UserDto;
import com.example.reborn.type.entity.User;

import com.example.reborn.type.etc.Role;
import lombok.Getter;
import lombok.ToString;



@Getter
@ToString
public class UserVo {

    private long userId;

    private String nickname;

    private String name;

    private String email;
    private Role role;

    private String address;
    private String detailAddress;
    private String phone;
    private Long point;
    private Long zipcode;

    private String gatePassword;


    public UserVo(UserDto dto) {
        this.userId = dto.getUserId();
        this.nickname = dto.getNickname();
        this.email = dto.getEmail();
        this.role = dto.getRole();
    }

    public UserVo(User user) {
        this.userId = user.getUserId();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.role = user.getRole();
    }


    public UserVo(User user, Address address)
    {
        this.userId = user.getUserId();
        this.nickname = user.getNickname();
        this.name=user.getName();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.address = address.getAddress();
        this.detailAddress = address.getAddressDetail();
        this.zipcode=address.getZipCode();
        this.gatePassword=address.getDoorPassword();
        this.phone = user.getPhone();
        this.point = user.getPoint();
    }
}