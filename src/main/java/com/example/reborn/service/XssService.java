package com.example.reborn.service;


import com.example.reborn.type.dto.XssResponseDto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@RequiredArgsConstructor
@Service
public class XssService {


    @Transactional
    public String stringTest(String input){
        return input;
    }

    @Transactional
    public XssResponseDto dtoTest(XssResponseDto xssRequestDto){

        return new XssResponseDto(
                xssRequestDto.getInput1(), xssRequestDto.getInput2()
        );
    }
}