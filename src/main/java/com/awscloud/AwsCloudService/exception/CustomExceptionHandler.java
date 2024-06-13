package com.awscloud.AwsCloudService.exception;

import com.awscloud.AwsCloudService.entity.DefaultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(code= HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Object illegalArgumentException(Exception e){
        log.error("{}",e);
        e.printStackTrace();
        log.info("Illegal Argument Exception measssage:{}",e.getLocalizedMessage());
        return new DefaultResponse(200,e.getLocalizedMessage(), e.getLocalizedMessage(), "200");
    }
}
