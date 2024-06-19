package com.projetae.miagiques.utilities;

import com.projetae.miagiques.utilities.BilletExceptions.BilletInexistant;
import com.projetae.miagiques.utilities.DelegationExceptions.DelegationExistante;
import com.projetae.miagiques.utilities.DelegationExceptions.DelegationInexistante;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TraitementExceptionDelegation {
    @ExceptionHandler(DelegationExistante.class)
    public ResponseEntity<String> gererDelegationExistante(HttpServletRequest requete, DelegationExistante ex) {
        return new ResponseEntity<>("La délégation est déjà existante", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DelegationInexistante.class)
    public ResponseEntity<String> gererDelegationInexistante(HttpServletRequest requete, DelegationInexistante ex) {
        return new ResponseEntity<>("La délégation n'existe pas", HttpStatus.NOT_FOUND);
    }
}
