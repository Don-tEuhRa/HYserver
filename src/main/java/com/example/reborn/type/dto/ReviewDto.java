package com.example.reborn.type.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import java.util.List;


@Getter
@Setter
public class ReviewDto {

    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9!?,.]*$")
    private String content;

    @Pattern(regexp = "^[1-5]+$")
    private Long star;

    @Pattern(regexp = "^[1-5]+$")
    private List<Long> weight;
}
