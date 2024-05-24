package com.projetae.miagiques.utilities.BilletExceptions;

import org.springframework.http.HttpStatus;

public class BilletInexistant extends Exception{

    private HttpStatus httpStatus;

    public BilletInexistant(){
        this.httpStatus = HttpStatus.CONFLICT;
    };

    public BilletInexistant(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpStatus getStatus(){
        return this.httpStatus;
    }
}
