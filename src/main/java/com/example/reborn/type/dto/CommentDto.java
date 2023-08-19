package com.example.reborn.type.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Getter
@Setter
public class CommentDto {

    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9!?,.]*$")
    private String content;
}
