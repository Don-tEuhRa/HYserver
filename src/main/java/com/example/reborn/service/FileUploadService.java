package com.example.reborn.service;

import com.example.reborn.exception.FileUploadFailException;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;



public interface FileUploadService {
    @Transactional
    String saveFile(MultipartFile multipartFile) throws FileUploadFailException;
}
