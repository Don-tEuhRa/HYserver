package com.example.reborn.controller;

import com.example.reborn.service.XssService;
import com.example.reborn.type.dto.XssResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/xss")
public class XssController {

    private final XssService xssService;

    @Operation(summary = "xss 테스트")
    @PostMapping("/parameter")
    public String strInput(@RequestParam String input){
        return xssService.stringTest(input);
    }


    @Operation(summary = "xss 테스트 JSon")
    @PostMapping("/dto")
    public XssResponseDto dtoInput(@Valid  @RequestBody XssResponseDto xssRequestDto){
        return xssService.dtoTest(xssRequestDto);
    }

}
