package com.example.reborn.exception;

import com.google.api.gax.rpc.NotFoundException;
import com.google.api.gax.rpc.PermissionDeniedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = RestController.class)
public class ExceptionControllerAdvice {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String exceptionHandler(java.lang.Exception e) {
        return "요청을 처리할 수 없습니다.";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String PermissionDeniedExceptionHandler(PermissionDeniedException e) {
        return "권한이 부족 합니다.";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String IllegalArgumentExceptionHandler(IllegalArgumentException e) {
        return "입력 값이 올바르지 않습니다.";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String NotFoundExceptionHandler(NotFoundException e) {
        return "요청 값을 찾을 수 없습니다.";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String NullPointerExceptionHandler(NullPointerException e) {
        return "입력 값이 올바르지 않습니다.";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String ArrayIndexOutOfBoundsExceptionHandler(ArrayIndexOutOfBoundsException e) {
        return "잘못된 접근 입니다.";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String NumberFormatExceptionHandler(NumberFormatException e) {
        return "입력 값이 올바르지 않습니다.";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String ClassCastExceptionHandler(ClassCastException e) {
        return "입력 값이 올바르지 않습니다.";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String IoExceptionHandler(java.io.IOException e) {
        return "입출력 에러가 발생하였습니다.";
    }
}