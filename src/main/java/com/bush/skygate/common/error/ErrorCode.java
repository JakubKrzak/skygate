package com.bush.skygate.common.error;

import org.springframework.http.HttpStatus;

public enum ErrorCode{
    FLIGHT_NOT_SCHEDULED(HttpStatus.CONFLICT),
    FLIGHT_NOT_FOUND(HttpStatus.NOT_FOUND),
    FLIGHT_EXISTS(HttpStatus.CONFLICT);

    private final HttpStatus status;

    ErrorCode(HttpStatus status){
        this.status = status;
    }

    public HttpStatus getStatus(){
        return status;
    }
}
