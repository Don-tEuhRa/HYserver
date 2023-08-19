package com.example.reborn.type.entity;


import javax.persistence.*;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
@Setter
public class UserInfoSet {

    @Id
    private Long userId;

}
