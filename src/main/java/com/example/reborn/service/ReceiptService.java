package com.example.reborn.service;

import com.example.reborn.repository.DonationStatusRepository;
import com.example.reborn.repository.ReceiptRepository;
import com.example.reborn.repository.UserRepository;
import com.example.reborn.type.dto.ReceiptDto;
import com.example.reborn.type.entity.DonationStatus;
import com.example.reborn.type.entity.Receipt;
import com.example.reborn.type.entity.User;
import com.example.reborn.type.etc.ReceiptStatus;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReceiptService {
private final ReceiptRepository receiptRepository;
    private final DonationStatusRepository donationStatusRepository;

    private final UserRepository userRepository;


    public void createReceipt(ReceiptDto dto, User user) {

        List<DonationStatus> donationList=donationStatusRepository.findAllByUser(user);
        if(!(donationList.isEmpty())){
            for(DonationStatus donationStatus:donationList){
                if(donationStatus.getStatusName().equals(ReceiptStatus.접수대기)){
                    //에러반환
                    throw new DuplicateRequestException("이미 접수중인 기부가 있습니다.");
                }
            }
        }
        if(!ReceiptDto.inputTest(dto))
        {
            throw new NotFoundException("필수 값을 입력해주세요");
        }

        Receipt receipt = receiptRepository.save(dto.toEntity(user));
        donationStatusRepository.save(DonationStatus.builder()
                .receipt(receipt)
                .statusName(ReceiptStatus.접수완료)
                        .user(user)
                        .createdAt(receipt.getCreatedAt())
                .build());

        user.setIsDonated(true);
        userRepository.save(user);

    }


    public void cancelReceipt(Long receiptId,User user) {
        Receipt receipt=receiptRepository.findByReceiptIdAndUser(receiptId,user)
                .orElseThrow(()->new NotFoundException("해당 접수번호가 없습니다."));
        DonationStatus donationStatus=donationStatusRepository.findByReceipt(
              receipt).orElseThrow(()->new NotFoundException("해당 접수번호가 없습니다."));
        if(donationStatus.getStatusName().equals(ReceiptStatus.접수대기)){
            donationStatusRepository.delete(donationStatus);
            receiptRepository.delete(receipt);
        }
        else{
            throw new DuplicateRequestException("이미 수거가 완료된 기부입니다.");
        }
    }




}
