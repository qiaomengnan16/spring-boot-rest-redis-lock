package com.demo.spring.redis.lock.config;

import com.demo.spring.redis.lock.exception.ServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice("com.demo")
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public String otherExceptionHandler(HttpServletResponse response, ServiceException ex) {
        response.setStatus(500);
        return ex.getMessage();
    }

}
