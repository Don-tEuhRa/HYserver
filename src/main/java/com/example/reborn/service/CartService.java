package com.example.reborn.service;

import com.example.reborn.exception.DuplicateInsertionException;
import com.example.reborn.repository.CartProductRelationRepository;
import com.example.reborn.repository.CartRepository;
import com.example.reborn.repository.ProductRepository;
import com.example.reborn.type.entity.Cart;
import com.example.reborn.type.entity.CartProductRelation;
import com.example.reborn.type.entity.Product;
import com.example.reborn.type.entity.User;
import com.example.reborn.type.vo.CartVo;
import com.example.reborn.utils.EntityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final ProductRepository productRepository;

    private final CartProductRelationRepository cartProductRelationRepository;

    private final CartRepository cartRepository;
    @Transactional
    public void createCart(Long productId, User user) {
        Product product = EntityUtil.findProductId(productRepository, productId);


        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new NotFoundException("장바구니가 없습니다."));
       if(product.getIsSold()==false) {
           if (cartProductRelationRepository.existsByCartAndProduct(cart, product)) {
               throw new DuplicateInsertionException("이미 장바구니에 담긴 상품입니다.");
           } else {
               cart.setUpdatedAt(LocalDateTime.now());
               cartRepository.save(cart);
               cartProductRelationRepository.save(CartProductRelation.builder()
                       .cart(cart)
                       .product(product)
                       .build());
           }
       }
       else
        throw new IllegalArgumentException("이미 판매된 상품입니다.");
    }




    @Transactional
    public void deleteSelectCart(Long productId, User user)
    {

        Cart cart = cartRepository.findByUser(user).orElseThrow(()-> new NotFoundException("장바구니가 없습니다."));
        if(cart.getUser()!=user)
        {
            throw new IllegalArgumentException("해당 장바구니에 접근할 수 없습니다.");
        }

        Product product = EntityUtil.findProductId(productRepository, productId);
        cartProductRelationRepository.deleteByCartAndProduct(cart, product);



    }

    @Transactional(readOnly=true)
    public List<CartVo> findAllCart(User user)
    {
        Cart cart=cartRepository.findByUser(user).orElseThrow(()-> new NotFoundException("장바구니가 없습니다."));
        List<CartProductRelation> cartList = cartProductRelationRepository.findAllByCart(cart);
        List<CartVo> results=cartList.stream().map(relation -> new CartVo(relation))
           .collect(Collectors.toList());
        return results;
    }


}
