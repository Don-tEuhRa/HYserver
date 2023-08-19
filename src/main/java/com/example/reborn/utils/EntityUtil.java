package com.example.reborn.utils;

import com.example.reborn.repository.*;
import com.example.reborn.type.entity.*;
import lombok.RequiredArgsConstructor;
import org.webjars.NotFoundException;

@RequiredArgsConstructor
public class EntityUtil {



    public static User findUserId(UserRepository userRepository, Long userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("해당 유저가 없습니다."));
    }

    public static Product findProductId(ProductRepository productRepository, Long productId) {
        return productRepository.findByProductId(productId)
                .orElseThrow(() -> new NotFoundException("해당 상품이 없습니다."));
    }

    public static Post findPostId(PostRepository postRepository, Long PostId) {
        return postRepository.findByPostId(PostId)
                .orElseThrow(() -> new NotFoundException("해당 문의가 없습니다. "));
    }



    public static Order findOrderId(OrderRepository orderRepository, Long orderId) {
        return orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new NotFoundException("해당 주문이 없습니다."));
    }

    public static Cart findCartId(CartRepository cartRepository, Long cartId) {
        return cartRepository.findByCartId(cartId)
                .orElseThrow(() -> new NotFoundException("해당 주문이 없습니다. "));
    }

    public static Order findOrderUser(OrderRepository orderRepository, User user) {
        return orderRepository.findByUser(user)
                .orElseThrow(() -> new NotFoundException("해당 주문이 없습니다. ="));
    }

    public static Address findAddress(UserAddressRelationRepository userAddressRelationRepository,User user){
        return  userAddressRelationRepository.findByUserId(user).get().getAddressId();
    }

    public static DonationStatus findStatus(DonationStatusRepository repository,Receipt receipt){
        return repository.findByReceipt(receipt).orElseThrow(()->new NotFoundException("해당 기부가 없습니다."));
    }
}
