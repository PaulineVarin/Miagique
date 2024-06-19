package com.projetae.miagiques.utilities.spectateurExceptions;

import org.springframework.http.HttpStatus;

public class TooManyBilletsException extends Exception {

    private HttpStatus httpStatus;

    public TooManyBilletsException(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpStatus getStatus(){
        return this.httpStatus;
    }
}
