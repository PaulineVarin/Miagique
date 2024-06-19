package com.projetae.miagiques.utilities;

import org.springframework.http.HttpStatus;

public class EpreuveInexistanteException extends Exception {

    private HttpStatus httpStatus;

    public EpreuveInexistanteException(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpStatus getStatus(){
        return this.httpStatus;
    }
}
