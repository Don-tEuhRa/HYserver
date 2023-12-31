package com.example.reborn.exception;

import org.apache.commons.fileupload.FileUploadException;

public class FileUploadFailException extends FileUploadException {
    private static final String DEFAULT_MESSAGE = "File Upload Fail!";

    public FileUploadFailException() {
        super(DEFAULT_MESSAGE);
    }

    public FileUploadFailException(String msg) {
        super(msg);
    }

    public FileUploadFailException(Throwable throwable) {
        super(DEFAULT_MESSAGE, throwable);
    }

    public FileUploadFailException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}