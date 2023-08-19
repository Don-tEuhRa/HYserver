package com.example.reborn.type.dto;

import com.example.reborn.type.entity.Receipt;
import com.example.reborn.type.entity.User;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class ReceiptDto {


        @NotEmpty
        private String address;

        @NotNull
        private String addressDetail;

        @Pattern(regexp = "^[0-9]*$", message = "숫자만 입력해주세요.")
        private Long zipCode;

        @Pattern(regexp = "^[0-9-]*$")
        private String date;
        private String gatePassword;

        @Pattern(regexp = "^[ㄱ-ㅎ가-힣]+$")
        private String name;

        @Pattern(regexp = "^[0-9-]*$")
        private String phoneNumber;

        public Receipt toEntity(User user){
                return Receipt.builder()
                        .address(address)
                        .addressDetail(addressDetail)
                        .zipCode(zipCode)
                        .date(date)
                        .gatePassword(gatePassword)
                        .name(name)
                        .phoneNumber(phoneNumber)
                        .user(user)
                        .createdAt(java.time.LocalDateTime.now())
                        .build();
        }


        public static boolean inputTest(ReceiptDto dto) {
                if (dto.address == null || dto.address.isEmpty()) {
                        return false;
                }
                else{}
                if (dto.addressDetail == null || dto.addressDetail.isEmpty()) {
                        return false;
                } else{}
                if (dto.zipCode == null) {
                        return false;
                } else{}
                if (dto.date == null || dto.date.isEmpty()) {
                        return false;
                } else{}
                if (dto.gatePassword == null || dto.gatePassword.isEmpty()) {
                        return false;
                } else{}
                if (dto.name == null || dto.name.isEmpty()) {
                        return false;
                } else{}
                if (dto.phoneNumber == null || dto.phoneNumber.isEmpty()) {
                        return false;
                } else{}

                return true;
        }
}
