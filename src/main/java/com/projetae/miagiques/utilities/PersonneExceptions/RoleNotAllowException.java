package com.projetae.miagiques.utilities.PersonneExceptions;

import org.springframework.http.HttpStatus;

public class RoleNotAllowException extends  Exception {
    private HttpStatus httpStatus;

    public RoleNotAllowException(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpStatus getStatus(){
        return this.httpStatus;
    }
}
