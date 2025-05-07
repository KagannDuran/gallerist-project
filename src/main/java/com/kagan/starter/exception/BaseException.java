package com.kagan.starter.exception;

public class BaseException extends RuntimeException {
    public BaseException(ErrorMessage errorMessage)
    {
        super(errorMessage.prepareErrorMessage());
    }
}
