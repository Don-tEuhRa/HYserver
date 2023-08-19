package com.example.reborn.service;


import com.example.reborn.exception.PermissionDeniedException;
import com.example.reborn.repository.DonationStatusRepository;
import com.example.reborn.repository.OrderRepository;
import com.example.reborn.repository.ReceiptRepository;
import com.example.reborn.type.entity.DonationStatus;
import com.example.reborn.type.entity.Order;
import com.example.reborn.type.entity.Receipt;
import com.example.reborn.type.entity.User;
import com.example.reborn.type.etc.OrderStatus;
import com.example.reborn.type.etc.ReceiptStatus;
import com.example.reborn.type.etc.Role;
import com.example.reborn.utils.EntityUtil;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final OrderRepository orderRepository;
    private final ReceiptRepository receiptRepository;

    private final DonationStatusRepository donationStatusRepository;

    @Transactional
    public void updateOrder(Long orderId, User user, OrderStatus status) throws PermissionDeniedException {
        Order order = EntityUtil.findOrderId(orderRepository, orderId);

        if(user.getRole()== Role.ADMIN) {
            order.setStatus(status);
            orderRepository.save(order);
        }
        else
        {
            throw new PermissionDeniedException("관리자가 아닙니다.");
        }
    }
    @Transactional
    public void updateReceipt(Long receiptId, ReceiptStatus status, User user) throws PermissionDeniedException {
        Receipt receipt=receiptRepository.findByReceiptId(receiptId)
                .orElseThrow(()->new NotFoundException("해당 접수번호가 없습니다."));
        DonationStatus donationStatus=donationStatusRepository.findByReceipt(
                receipt).orElseThrow(()->new NotFoundException("해당 접수번호가 없습니다."));
        if(user.getRole()== Role.ADMIN){
            donationStatus.setStatusName(status);


            if(status==ReceiptStatus.수거완료){
                donationStatus.setPickupAt(LocalDateTime.now());

            }


            donationStatusRepository.save(donationStatus);
        }
        else{
            throw new PermissionDeniedException("관리자만 접근 가능합니다.");
        }

    }

    @Transactional
    public void cancelReceiptForAdmin(Long receiptId,User user) throws PermissionDeniedException {
        Receipt receipt=receiptRepository.findByReceiptId(receiptId)
                .orElseThrow(()->new NotFoundException("해당 접수번호가 없습니다."));
        DonationStatus donationStatus=donationStatusRepository.findByReceipt(
                receipt).orElseThrow(()->new NotFoundException("해당 접수번호가 없습니다."));

        if(user.getRole()== Role.ADMIN) {
            if(donationStatus.getStatusName().equals(ReceiptStatus.접수대기)){
                donationStatusRepository.delete(donationStatus);
                receiptRepository.delete(receipt);
            }
            else{
                throw new DuplicateRequestException("이미 접수가 완료된 기부입니다.");
            }
        }
        else{
            throw new PermissionDeniedException("관리자만 접근 가능합니다.");
        }

    }
}
