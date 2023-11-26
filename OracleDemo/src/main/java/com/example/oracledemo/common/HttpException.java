package com.example.oracledemo.common;

public class HttpException extends RuntimeException{
    protected String message;
    protected Integer httpStatusCode = 500;

    public  HttpException(String message){
        this.message =message;
    }
    public String getErrorMessage() {
        return message;
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

}
