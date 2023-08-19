package com.example.reborn.service;

import com.example.reborn.repository.DonationStatusRepository;
import com.example.reborn.type.entity.DonationStatus;
import com.example.reborn.type.entity.User;
import com.example.reborn.type.etc.ReceiptStatus;
import com.example.reborn.type.vo.ProgressVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgressService {

    private final DonationStatusRepository donationStatusRepository;


    public List<ProgressVo> findAll(User user)
    {
        List<DonationStatus> donationStatusList = donationStatusRepository.findAllByUser(user);
        List<ProgressVo> progressVoList = new ArrayList<>();
        ReceiptStatus statusName=null;
        Long count=0L;
        for(DonationStatus donationStatus : donationStatusList)
        {
            if(donationStatus.getStatusName().getValue()==ReceiptStatus.접수대기.getValue())//ReceiptStatus enum의 key값이 1 작은 DonationStatus 반환
                continue;
            else if(donationStatus.getStatusName().getValue()%2==0)//ReceiptStatus enum의 key값이 1 더 큰 DonationStatus 반환
            { statusName=ReceiptStatus.getReceiptStatus(donationStatus.getStatusName().getValue()+1);
            count++;}
            else//ReceiptStatus enum의 key값이 1 작은 DonationStatus 반환
            {   statusName=ReceiptStatus.getReceiptStatus(donationStatus.getStatusName().getValue());
            count++;}

            ProgressVo progressVo = new ProgressVo(donationStatus,statusName, count);
            progressVoList.add(progressVo);
        }
        return progressVoList;
    }


}
