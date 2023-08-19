package com.example.reborn.controller;
import com.example.reborn.service.MessageService;
import com.example.reborn.type.dto.MessageDto;
import com.example.reborn.type.dto.ResponseModel;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/message")
public class MessageController {


    private final MessageService messageService;



    @Operation(summary = "문자 메시지 단방향 전송")
    @PostMapping("/send-one")
    public ResponseModel sendOne(@RequestBody MessageDto dto) {

        ResponseModel responseModel = ResponseModel.builder().build();
        responseModel.addData("response",messageService.sendMessage(dto.getUserPhone(),dto.getText()));
        return  responseModel;
    }
}