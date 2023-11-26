package com.example.oracledemo.config;

import com.example.oracledemo.common.ApiResponseObject;
import com.example.oracledemo.common.HttpException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice(annotations = {RestController.class, Controller.class})
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(HttpException.class)
    @ResponseBody
    public ApiResponseObject<String> httpException(HttpException   e) {
        e.printStackTrace();
        return new ApiResponseObject<>(405, e.getErrorMessage(),e.getErrorMessage());
    }


}
