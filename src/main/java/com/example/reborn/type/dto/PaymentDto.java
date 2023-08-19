package com.example.reborn.type.dto;


import com.example.reborn.type.etc.PaymentMethod;
import lombok.Getter;
import lombok.Setter;
;

import java.util.List;

@Getter
@Setter
public class PaymentDto {


private Long usePoint;
private PaymentMethod payMethod;
private List<Long> productId;





}
