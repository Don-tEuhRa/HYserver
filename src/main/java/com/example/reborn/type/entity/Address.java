package com.example.reborn.type.entity;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.*;

@Entity
@Table(name = "address")
@NoArgsConstructor
@Getter
@Setter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long addressId;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String addressDetail;

    @Column(nullable = false)
    private Long zipCode;


    @Column(nullable = true)
    private String doorPassword;

    @Builder
    public Address(String address, String addressDetail,Long zipCode,String doorPassword){
        this.address = address;
        this.addressDetail = addressDetail;
        this.zipCode = zipCode;
        this.doorPassword=doorPassword;
    }
}