package com.projetae.miagiques.utilities;

import com.projetae.miagiques.utilities.BilletExceptions.BilletAnnulationImpossible;
import com.projetae.miagiques.utilities.BilletExceptions.BilletInexistant;
import com.projetae.miagiques.utilities.PersonneExceptions.RoleNotAllowException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TraitementExceptions {
    @ExceptionHandler(BilletInexistant.class)
    public ResponseEntity<String> gererBilletInexistant (HttpServletRequest requete, BilletInexistant ex) {
        return new ResponseEntity<>("Ce billet n'est pas trouvable dans la base", ex.getStatus()) ;
    }

    @ExceptionHandler(BilletAnnulationImpossible.class)
    public ResponseEntity<String> gererBilletAnnulationImpossible (HttpServletRequest requete, BilletAnnulationImpossible ex) {
        return new ResponseEntity<>("Impossible d'annuler ce billet", HttpStatus.BAD_REQUEST) ;
    }

    @ExceptionHandler(RoleNotAllowException.class)
    public ResponseEntity<String> gererRoleNotAllowException (HttpServletRequest requete, RoleNotAllowException ex){
        return new ResponseEntity<>("You are not authorized to access this service", ex.getStatus());
    }
}
