package com.example.demo.kakaopay;

import lombok.Getter;

@SuppressWarnings("serial")
@Getter
public class BusinessLogicException extends RuntimeException {
    private Exception exceptionCode;

    public BusinessLogicException(Exception exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }
}
