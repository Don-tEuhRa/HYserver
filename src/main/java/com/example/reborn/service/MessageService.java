package com.example.reborn.service;

import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class MessageService {

    @Value("${coolms.apikey}")
    private String apiKey;

    @Value("${coolms.apiSecret}")
    private String apiSecret;

    @Value("${coolms.fromNumber}")
    private String fromNumber;


    private DefaultMessageService messageService;
    @PostConstruct
    public void initialize() {
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecret, "https://api.coolsms.co.kr");
    }




    public SingleMessageSentResponse sendMessage(String userPhone,String text)
    {
        Message message = new Message();
        // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
        message.setFrom(fromNumber);
        message.setTo(userPhone);
        message.setText(text);

        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        return response;
    }
}