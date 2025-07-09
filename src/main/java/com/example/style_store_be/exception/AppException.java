package com.example.style_store_be.exception;

public class AppException extends RuntimeException{
    private Errorcode errorCode;

    public AppException(Errorcode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public Errorcode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Errorcode errorCode) {
        this.errorCode = errorCode;
    }
}
