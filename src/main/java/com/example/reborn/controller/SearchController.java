package com.example.reborn.controller;

import com.example.reborn.service.SearchService;
import com.example.reborn.type.dto.ResponseModel;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    @Operation(summary="탑5검색어")
    @GetMapping("/top")
    public ResponseModel searchTop()
    {
        ResponseModel responseModel = ResponseModel.builder().build();
        responseModel.addData("keywords",  searchService.topSearch());
        return responseModel;

    }
}
