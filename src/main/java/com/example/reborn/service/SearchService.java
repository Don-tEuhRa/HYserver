package com.example.reborn.service;

import com.example.reborn.repository.SearchCountRepository;
import com.example.reborn.type.entity.SearchCount;
import com.example.reborn.type.vo.SearchVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final SearchCountRepository searchCountRepository;

    public void searchCount(String keyword)
    {
        List<SearchCount> count =searchCountRepository.findAllByKeywordContaining(keyword);

        for(SearchCount word:count)
        {
            word.setCount(word.getCount()+1);
        }
    }

    public List<SearchVo> topSearch()
    {
        List<SearchCount> list=searchCountRepository.findTop5ByOrderByCountDesc();
        List<SearchVo> voList=new ArrayList<>();
        for(SearchCount search:list)
        {
            SearchVo vo = new SearchVo(search);
            voList.add(vo);
        }

        return voList;
    }
}
