package com.bush.skygate.common.error;

import org.springframework.http.HttpStatus;

public enum ErrorCode{
    FLIGHT_NOT_SCHEDULED(HttpStatus.CONFLICT),
    FLIGHT_NOT_FOUND(HttpStatus.NOT_FOUND);

    private final HttpStatus status;

    ErrorCode(HttpStatus status){
        this.status = status;
    }

    public HttpStatus getStatus(){
        return status;
    }
}
