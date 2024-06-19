package com.projetae.miagiques.utilities.EpreuveExceptions;

import org.springframework.http.HttpStatus;

public class EpreuveInexistante extends Exception {

    private HttpStatus httpStatus;

    public EpreuveInexistante(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpStatus getStatus(){
        return this.httpStatus;
    }
}
