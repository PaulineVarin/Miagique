package com.projetae.miagiques.utilities;

import com.projetae.miagiques.utilities.BilletExceptions.BilletAnnulationImpossible;
import com.projetae.miagiques.utilities.BilletExceptions.BilletInexistant;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TraitementExceptionsBillet {
    @ExceptionHandler(BilletInexistant.class)
    public ResponseEntity<String> gererBilletInexistant (HttpServletRequest requete, BilletInexistant ex) {
        return new ResponseEntity<>("Ce billet n'est pas trouvable dans la base", HttpStatus.CONFLICT) ;
    }

    @ExceptionHandler(BilletAnnulationImpossible.class)
    public ResponseEntity<String> gererBilletAnnulationImpossible (HttpServletRequest requete, BilletAnnulationImpossible ex) {
        return new ResponseEntity<>("Impossible d'annuler ce billet", HttpStatus.BAD_REQUEST) ;
    }
}
