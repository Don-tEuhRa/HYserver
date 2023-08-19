package com.example.reborn.type.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
@Setter
public class SurveyDto {

    @Pattern(regexp = "^[1-5]+$")
    private List<Long> weight;

}
