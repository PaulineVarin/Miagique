package com.projetae.miagiques.utilities.BilletExceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BilletAchatImpossible extends Exception {

    private HttpStatus status;

    public BilletAchatImpossible(HttpStatus status){
        this.status = status;
    }
}
