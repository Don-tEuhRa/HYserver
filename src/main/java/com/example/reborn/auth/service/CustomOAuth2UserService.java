package com.example.reborn.auth.service;

import com.example.reborn.auth.factory.OAuth2UserInfoFactory;
import com.example.reborn.repository.*;
import com.example.reborn.type.dto.OAuthUserInfo;
import com.example.reborn.type.dto.UserPrincipal;
import com.example.reborn.type.entity.*;
import com.example.reborn.type.etc.OAuthProvider;
import com.example.reborn.type.etc.Role;
import com.example.reborn.utils.NicknameGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService  {

    private final OAuthUserRepository oAuthUserRepository;
    private final UserRepository userRepository;
    private final UserInfoSetRepository userInfoSetRepository;
    private final CartRepository cartRepository;

    private final NicknameGenerator nicknameGenerator;

    private final AddressRepository addressRepository;

    private final UserAddressRelationRepository userAddressRelationRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        return process(userRequest, oAuth2User);
    }

    OAuth2User process(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        OAuthProvider oAuthProvider = OAuthProvider.valueOf(
                userRequest.getClientRegistration().getClientName().toUpperCase());
        OAuthUserInfo oAuth2UserInfo = OAuth2UserInfoFactory.of(oAuthProvider, oAuth2User.getAttributes());
        OAuth2User user = saveOrUpDate(oAuth2UserInfo);
        return user;
    }

    OAuth2User saveOrUpDate(OAuthUserInfo oAuth2UserInfo) {
        Optional<OAuthUser> savedUser;
        savedUser = oAuthUserRepository.findByProviderUserIdAndOap(
                oAuth2UserInfo.getId(),
                OAuthProvider.valueOf(oAuth2UserInfo.getOAuthProviderName().toUpperCase())
        );
        User user;
        OAuthUser oAuthUser;
        if (savedUser.isPresent()) {
            oAuthUser = savedUser.get();
            user = oAuthUser.getUser();
        } else {
            user = User.builder()

                    .userId( userRepository.findTopByOrderByUserIdDesc().getUserId() + 1L)
                    .name(oAuth2UserInfo.getName())
                    .nickname(nicknameGenerator.generate())
                    .email(oAuth2UserInfo.getEmail())
                    .role(Role.ADMIN)//이후에 User로 수정
                    .oauth_provider(OAuthProvider.GOOGLE)
                    .isDonated(false)
                    .point(0L)
                    .phone(" ")
                    .build();
            user = userRepository.save(user);
            Address address=Address.builder()
                            .address(" ")
                            .addressDetail(" ")
                             .zipCode(0L)
                             .doorPassword(" ").build();
            addressRepository.save(address);
            UserAddressRelation userAddressRelation=  UserAddressRelation.builder()
                            .userId(user)
                                    .addressId(address)
                                            .build();
            userAddressRelationRepository.save(userAddressRelation);
            userInfoSetRepository.save(new UserInfoSet(user.getUserId()));

            Cart cart =Cart.builder()
                    .isOrdered(false)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .user(user)
                    .build();
            cartRepository.save(cart);
            oAuthUser = OAuthUser.builder()
                    .providerUserId(oAuth2UserInfo.getId())
                    .email(oAuth2UserInfo.getEmail())
                    .name(oAuth2UserInfo.getName())
                    .oap(OAuthProvider.valueOf(oAuth2UserInfo.getOAuthProviderName().toUpperCase()))
                    .user(user)
                    .build();
        }
        oAuthUserRepository.save(oAuthUser);
        return UserPrincipal.create(user);
    }

}