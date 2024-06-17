package com.projetae.miagiques.utilities;

import com.projetae.miagiques.utilities.InfrastructureSportiveExceptions.InfrastructureSportiveInexistante;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TraitementExceptionsInfrastrcutureSportive {
    @ExceptionHandler(InfrastructureSportiveInexistante.class)
    public ResponseEntity<String> gererInfrastructureSprotiveInexistant (HttpServletRequest requete, InfrastructureSportiveInexistante ex) {
        return new ResponseEntity<>("Cette infrastructure n'existe pas", HttpStatus.NOT_FOUND) ;
    }
}
