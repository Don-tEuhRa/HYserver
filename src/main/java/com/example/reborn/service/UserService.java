package com.example.reborn.service;

import com.example.reborn.auth.jwt.AuthToken;
import com.example.reborn.auth.jwt.AuthTokenProvider;
import com.example.reborn.repository.*;
import com.example.reborn.type.dto.UserDto;
import com.example.reborn.type.dto.UserPrincipal;
import com.example.reborn.type.entity.*;
import com.example.reborn.type.etc.OAuthProvider;
import com.example.reborn.type.etc.Role;
import com.example.reborn.type.vo.UserVo;
import com.example.reborn.utils.NicknameGenerator;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AuthTokenProvider authTokenProvider;

    private final UserInfoSetRepository userInfoSetRepository;

    private final UserAddressRelationRepository userAddressRelationRepository;
    private final UserRepository userRepository;

    private final OAuthUserRepository oAuthUserRepository;

    private final AddressRepository addressRepository;


    private static final long TOKEN_DURATION = 1000L * 60L * 60L * 24L;

    private final FirebaseApp firebaseApp;

    private final NicknameGenerator nicknameGenerator;
    public void signup(UserDto dto) {





    }


    public UserPrincipal login(String token) {
        AuthToken authToken = authTokenProvider.convertToken(token);

        if (authToken.validate()) {

            Authentication authentication = authTokenProvider.getAuthentication(authToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ((UserPrincipal)authentication.getPrincipal());
        }
        return null;

    }


    public User saveUserFromFirebase(String uid) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance(firebaseApp);
        UserRecord userRecord;
        try {
            userRecord = firebaseAuth.getUser(uid);
        } catch (FirebaseAuthException e) {
            throw new NotFoundException("Could not find user in Firebase");
        }
//email 중복 x

        List<User> users = userRepository.findAllByEmail(userRecord.getEmail());
        if (!users.isEmpty()) {
            User existingUser = users.get(0); // Choose the first user from the list
            return existingUser;
        }

// Rest of the code to create a new user


        User newUser = User.builder()
                .userId(userRepository.findTopByOrderByUserIdDesc().getUserId() + 1L)
                .nickname(nicknameGenerator.generate())
                .name(userRecord.getDisplayName())
                .email(userRecord.getEmail())
                .role(Role.USER)
                .oauth_provider(OAuthProvider.GOOGLE)
                .point(0L)
                .isDonated(false)
                .phone(" ")
                .build();
        userRepository.save(newUser);


        Address address=Address.builder()
                .address(" ")
                .addressDetail(" ")
                .zipCode(0L)
                .doorPassword(" ").build();
        addressRepository.save(address);
        UserAddressRelation userAddressRelation=  UserAddressRelation.builder()
                .userId(newUser)
                .addressId(address)
                .build();

        userAddressRelationRepository.save(userAddressRelation);




        userInfoSetRepository.save(new UserInfoSet(newUser.getUserId()));


        OAuthUser oAuthUser = OAuthUser.builder()
                .providerUserId(userRecord.getProviderId())
                .email(userRecord.getEmail())
                .name(userRecord.getDisplayName())
                //  .picture(userRecord.getPhotoUrl())
                .oap(OAuthProvider.GOOGLE)
                .user(newUser)
                .build();
        oAuthUserRepository.save(oAuthUser);

        return newUser;
    }
    @Transactional
    public AuthToken getUserToken(User user) {

        UserPrincipal userPrincipal = UserPrincipal.create(user);

        Date expiry = new Date();
        expiry.setTime(expiry.getTime() + (TOKEN_DURATION));

        AuthToken authToken = authTokenProvider.createToken(userPrincipal, expiry);
        return authToken;
    }
    @Transactional(readOnly = true)
    public UserVo getUser(User user) {

        UserAddressRelation userAddressRelation=userAddressRelationRepository.findByUserId(user).orElseThrow();
        Address address=userAddressRelation.getAddressId();

        UserVo vo = new UserVo(user,address);
        return vo;
    }
}