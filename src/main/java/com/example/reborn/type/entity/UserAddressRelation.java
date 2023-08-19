package com.example.reborn.type.entity;

import javax.persistence.*;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "user_address_relation")
@NoArgsConstructor
@Getter
public class UserAddressRelation {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long userAddressRelationId;

        @ManyToOne
        @JoinColumn(name = "user_id",nullable = false)
        @OnDelete(action = OnDeleteAction.CASCADE)
        private User userId;

        @ManyToOne
        @JoinColumn(name = "address_id",nullable = false)
        @OnDelete(action = OnDeleteAction.CASCADE)
        private Address addressId;

        @Builder
        public UserAddressRelation(User userId, Address addressId){
            this.userId = userId;
            this.addressId = addressId;
        }
}
