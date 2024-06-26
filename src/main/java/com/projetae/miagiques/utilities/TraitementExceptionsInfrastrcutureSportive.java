package com.projetae.miagiques.utilities;

import com.projetae.miagiques.utilities.InfrastructureSportiveExceptions.InfrastructureSportiveExiste;
import com.projetae.miagiques.utilities.InfrastructureSportiveExceptions.InfrastructureSportiveInexistante;
import com.projetae.miagiques.utilities.InfrastructureSportiveExceptions.InfrastructureSportiveReference;
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

    @ExceptionHandler(InfrastructureSportiveExiste.class)
    public ResponseEntity<String> gererInfrastructureSprotiveExiste (HttpServletRequest requete, InfrastructureSportiveExiste ex) {
        return new ResponseEntity<>("Une infrastructure existe deja avec ce nom",  HttpStatus.CONFLICT) ;
    }

    @ExceptionHandler(InfrastructureSportiveReference.class)
    public ResponseEntity<String> gererInfrastructureSprotiveReference (HttpServletRequest requete, InfrastructureSportiveReference ex) {
        return new ResponseEntity<>("Impossible de supprimer l'infrastructure, des epreuves sont référencées",  HttpStatus.CONFLICT) ;
    }
}
