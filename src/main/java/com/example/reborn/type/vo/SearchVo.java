package com.example.reborn.type.vo;

import com.example.reborn.type.entity.SearchCount;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SearchVo {

    private String keyword;

    public SearchVo(SearchCount search)
    {
        this.keyword=search.getKeyword();
    }
}


