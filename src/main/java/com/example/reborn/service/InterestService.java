package com.example.reborn.service;

import com.example.reborn.exception.PermissionDeniedException;
import com.example.reborn.repository.InterestRepository;
import com.example.reborn.repository.ProductRepository;

import com.example.reborn.type.entity.Interest;
import com.example.reborn.type.entity.Product;
import com.example.reborn.type.entity.User;
import com.example.reborn.type.etc.Role;
import com.example.reborn.type.vo.ProductVo;
import com.example.reborn.utils.EntityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class InterestService {

    private final InterestRepository interestRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;
    @Transactional
    public void saveInterest(Long productId, User user) throws PermissionDeniedException {

        if(user.getRole() == Role.ADMIN) {
            throw new PermissionDeniedException("관리자는 찜 할 수 없습니다.");
        }

        if(user.getRole() != Role.USER) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }

        Product product = EntityUtil.findProductId(productRepository,productId);
        Interest interest = Interest.builder()
                .product(product)
                .user(user)
                .build();

        interestRepository.save(interest);
    }
    @Transactional
    public void deleteInterest(Long productId, User user) {

        Product product = EntityUtil.findProductId(productRepository,productId);
        Interest interest = interestRepository.findByProductAndUser(product, user).orElseThrow(
                () -> new IllegalArgumentException("해당 찜 한 상품이 없습니다.")
        );

        interestRepository.delete(interest);
    }
    @Transactional(readOnly = true)
    public List<ProductVo> getInterestList(User user) {

        List<Interest> interestList = interestRepository.findAllByUser(user);

        List<ProductVo> productVoList = interestList.stream().map(
                interest -> productService.getProduct(interest.getProduct().getProductId(),user)
        ).collect(Collectors.toList());

        return productVoList;
    }

}
