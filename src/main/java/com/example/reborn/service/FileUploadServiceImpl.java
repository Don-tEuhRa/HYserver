package com.example.reborn.service;

import com.example.reborn.exception.FileUploadFailException;
import com.example.reborn.handler.FileHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileUploadServiceImpl implements FileUploadService {

    private final FileHandler fileHandler;

    @Value("${resource.file.url}")
    private String fileURL;
    @Transactional
    @Override
    public String saveFile(MultipartFile multipartFile) throws FileUploadFailException {
        String realFilename = fileHandler.saveFile(multipartFile);
        return fileURL + "/" + realFilename;
    }
}