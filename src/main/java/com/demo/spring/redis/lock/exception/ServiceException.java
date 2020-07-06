package com.demo.spring.redis.lock.exception;

public class ServiceException extends Throwable {

    private String message;

    public ServiceException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
