package com.example.reborn.type.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class AddressDto {

    private String name;

    @NotEmpty
    private String address;

    @NotNull
    private String addressDetail;

    @Pattern(regexp = "^[0-9]*$", message = "숫자만 입력해주세요.")
    private Long zipCode;

    private String doorPassword;

    @Pattern(regexp = "^[0-9-]*$")
    private String phoneNumber;

    public static boolean inputTest(AddressDto dto) {
        if(dto.getName()==null)
        {
            return false;
        }
        if(dto.getAddress()==null)
        {
            return false;
        }
        if(dto.getAddressDetail()==null)
        {
            return false;
        }
        if(dto.getZipCode()==null)
        {
            return false;
        }
        if(dto.getPhoneNumber()==null)
        {
            return false;
        }
        if(dto.getDoorPassword()==null)
        {
            return false;
        }
        return true;
    }


}