package com.example.reborn.controller;

import com.example.reborn.exception.FileUploadFailException;
import com.example.reborn.service.FileUploadService;
import com.example.reborn.type.dto.ResponseModel;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/file")
public class FileUploadController {
    private final FileUploadService fileUploadService;

    @Operation(summary = "파일 업로드", description = "ROLE_ADMIN")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public synchronized ResponseModel upload(
            @RequestParam(value = "file", required = true) MultipartFile multipartFile
    ) throws FileUploadFailException {
        String url = fileUploadService.saveFile(multipartFile);
        ResponseModel responseModel = ResponseModel.builder().build();
        responseModel.addData("url", url);
        return responseModel;
    }
}
