package com.example.reborn.type.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Table(name = "search_count")
@NoArgsConstructor
@Getter
@Setter
public class SearchCount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long searchId;

    @Column(nullable = false)
    private String keyword;

    @Column(nullable = true)
    @ColumnDefault("0L")
    private Long count;

    @Builder
    public SearchCount(String keyword,Long count)
    {
        this.keyword=keyword;
        this.count=count;
    }

}
