package com.example.reborn.service;

import com.example.reborn.exception.FileUploadFailException;
import com.example.reborn.handler.FileHandler;
import com.example.reborn.repository.*;
import com.example.reborn.type.dto.AddressDto;
import com.example.reborn.type.dto.MypageDto;
import com.example.reborn.type.entity.*;
import com.example.reborn.type.etc.OrderStatus;
import com.example.reborn.type.etc.ReceiptStatus;
import com.example.reborn.type.vo.*;
import com.example.reborn.utils.EntityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class MypageService {

    private final AddressRepository addressRepository;

    private final UserAddressRelationRepository userAddressRelationRepository;

    private final ProductReceiptRelationRepository productReceiptRelationRepository;

    private final UserRepository userRepository;

    private final OrderRepository orderRepository;

    private final ReceiptRepository receiptRepository;
    private final DonationStatusRepository donationStatusRepository;

    private final OrderProductRelationRepository orderProductRelationRepository;


    private final ProductRepository productRepository;

    private final FileHandler fileHandler;


    public MypageVo mypage(User user) {
        List<Order> orderList = orderRepository.findAllByUser(user);

        List<Receipt> receiptList = receiptRepository.findAllByUser(user);
        Long donationCount = donationStatusRepository.countByUserAndStatusName(user, ReceiptStatus.기부완료);

        MypageDto dto = MypageDto.builder()
                .user(user)
                .order(orderList)
                .receipt(receiptList)
                .build();
        Long donationPoint=0L;
List<Product> products=new ArrayList<>();

        for(Order order:orderList)
        {   List<OrderProductRelation> productList=orderProductRelationRepository.findAllByOrder(order);
            for(OrderProductRelation product:productList) {
                donationPoint+= product.getProduct().getPrice();
            }

        }
        MypageVo vo = new MypageVo(dto,donationPoint,donationCount);
        return vo;
    }

    public void setAddress(User user, AddressDto dto) {
        if(AddressDto.inputTest(dto)){
        Address address=userAddressRelationRepository.findByUserId(user).get().getAddressId();
        address.setAddress(dto.getAddress());
        address.setAddressDetail(dto.getAddressDetail());
        address.setZipCode(dto.getZipCode());
        address.setDoorPassword(dto.getDoorPassword());
        user.setPhone(dto.getPhoneNumber());
        user.setName(dto.getName());
        userRepository.save(user);
        addressRepository.save(address);}
        else {
            throw new IllegalArgumentException("값을 모두 입력해주세요");
        }
    }




    public void profileImgSave(User user, MultipartFile file) throws FileUploadFailException {
        String profileImgUrl = fileHandler.saveFile(file);
        user.setProfileImgUrl(profileImgUrl);
        userRepository.save(user);
    }


    public UserVo info(User user) {
        if(user.getPhone()!=null){
            UserVo vo=new UserVo(user);
        }
        UserVo vo=new UserVo(user);
        return vo;
    }

    public List<DonationStatusVo> getDonation(User user) {


        List<DonationStatus> donationStatusList = donationStatusRepository.findAllByUser(user);
        List<DonationStatusVo> donationStatusVoList = new ArrayList<>();
        DonationStatusVo vo = null;
        for (DonationStatus donationStatus : donationStatusList) {

            if(donationStatus.getStatusName().getValue()<=9L)
            {
                vo = new DonationStatusVo(donationStatus);
            }
            else
            {
                ProductReceiptRelation productReceiptRelation = productReceiptRelationRepository.findByReceipt(donationStatus.getReceipt()).orElseThrow();
                Product product=  productReceiptRelation.getProduct();
                vo = new DonationStatusVo(donationStatus,product);
            }


            donationStatusVoList.add(vo);
        }
        return donationStatusVoList;
    }

    public DonationCountVo getDonationCount(User user) {

        //receiptStatus가 ReceiptStatus.접수대기인 것들의 개수
        Long receiptStatusCount = donationStatusRepository.countByUserAndStatusName(user, ReceiptStatus.접수완료);
        Long pickupCount =donationStatusRepository.countByUserAndStatusName(user, ReceiptStatus.수거중);
        Long reformCount = donationStatusRepository.countByUserAndStatusName(user, ReceiptStatus.리폼중);
        Long arriveCount = donationStatusRepository.countByUserAndStatusName(user, ReceiptStatus.배달완료);
        Long productCount = donationStatusRepository.countByUserAndStatusName(user, ReceiptStatus.판매중);
        Long donationCount = donationStatusRepository.countByUserAndStatusName(user, ReceiptStatus.기부완료);

        DonationCountVo vo= new DonationCountVo(receiptStatusCount,pickupCount,reformCount,arriveCount,productCount,donationCount);
        return vo;
    }

    public OrderCountVo getOrderCount(User user) {
        Long orderCount = (long)orderRepository.findAllByUser(user).size();
        Long payCount = orderRepository.countByUserAndStatus(user, OrderStatus.결제성공);
        Long deliveryCount = orderRepository.countByUserAndStatus(user, OrderStatus.배송중);
        Long deliveredCount = orderRepository.countByUserAndStatus(user, OrderStatus.배달완료);
        Long completeCount = orderRepository.countByUserAndStatus(user, OrderStatus.구매확정);
        OrderCountVo vo = new OrderCountVo(orderCount,payCount,deliveryCount,deliveredCount,completeCount);
        return vo;

    }

    public List<OrderProductVo> getOrder(User user) {
        List<Order> orderList = orderRepository.findAllByUser(user);
        List<OrderProductVo> orderVoList = new ArrayList<>();

        List<OrderProductRelation> relations=new ArrayList<>();
        OrderProductVo vo = null;

        Address address= EntityUtil.findAddress(userAddressRelationRepository,user);
        for (Order order : orderList) {
            List<OrderProductRelation> orders=orderProductRelationRepository.findAllByOrder(order);
            for(OrderProductRelation relation:orders)
            {
                vo = new OrderProductVo(relation,address);
                orderVoList.add(vo);
            }

        }
        return orderVoList;
    }
}