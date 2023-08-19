package com.example.reborn.service;

import com.example.reborn.exception.PermissionDeniedException;
import com.example.reborn.repository.*;
import com.example.reborn.type.dto.PaymentDto;
import com.example.reborn.type.entity.*;
import com.example.reborn.type.etc.OrderStatus;
import com.example.reborn.type.etc.ReceiptStatus;
import com.example.reborn.type.vo.OrderVo;
import com.example.reborn.utils.EntityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final ProductRepository productRepository;


    private final ProductReceiptRelationRepository productReceiptRelationRepository;

    private final OrderRepository orderRepository;

    private final OrderProductRelationRepository orderProductRelationRepository;

    private final CartRepository cartRepository;

private final UserAddressRelationRepository userAddressRelationRepository;
    private final UserRepository userRepository;

    private final DonationStatusRepository donationStatusRepository;

    public void saveOrder(PaymentDto dto, User user) throws PermissionDeniedException {
        Long point=user.getPoint();
        Long usePoint=dto.getUsePoint();
        if(user.getPoint()-dto.getUsePoint()<0)
        {   throw new PermissionDeniedException("더 큰 포인트를 사용하였습니다.");

        }
        Order order = Order.builder()
                .user(user)
                .paymentMethod(dto.getPayMethod())
                .point(point-usePoint)
                .created_at(LocalDateTime.now())
                .status(OrderStatus.결제성공)
                .build();
        user.setPoint(point-usePoint);
       // user.setIsDonated(true);
        orderRepository.save(order);
        userRepository.save(user);

        for(Long id : dto.getProductId())
        {
            Product product = EntityUtil.findProductId(productRepository, id);


            OrderProductRelation orderProductRelation = OrderProductRelation.builder()
                    .order(order)
                    .product(product)
                    .build();
            orderProductRelationRepository.save(orderProductRelation);
            product.setIsSold(true);
            productRepository.save(product);

            ProductReceiptRelation relation=productReceiptRelationRepository.findByProduct(product).orElseThrow();
            DonationStatus status=EntityUtil.findStatus(donationStatusRepository,relation.getReceipt());
            status.setStatusName(ReceiptStatus.기부완료);
            donationStatusRepository.save(status);
        }

    }

    public void deleteOrder(Long orderId, User user) {

        Order order = EntityUtil.findOrderId(orderRepository, orderId);
        if(!order.getUser().equals(user))
        {
            throw new IllegalArgumentException("주문자가 아닙니다.");
        }
        if(order.getStatus().equals("ORDERED"))//ORDERED 상태일때만 취소가능
        {
            orderRepository.delete(order);
        }
        else
        {
            throw new IllegalArgumentException("주문취소가 불가능합니다.");
        }
    }
    @Transactional(readOnly = true)
    public List<OrderVo> findAllOrder(User user) {
        List<Order> orderList=orderRepository.findAllByUser(user);

        Address address= EntityUtil.findAddress(userAddressRelationRepository,user);
        List<OrderVo> results=orderList.stream()
                .map(order -> new OrderVo(order,address))
                .collect(Collectors.toList());
        return results;

    }


}
