package com.projetae.miagiques.utilities;

import com.projetae.miagiques.utilities.BilletExceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TraitementExceptionsBillet {
    @ExceptionHandler(BilletInexistant.class)
    public ResponseEntity<String> gererBilletInexistant (HttpServletRequest requete, BilletInexistant ex) {
        return new ResponseEntity<>("Ce billet n'est pas trouvable dans la base", ex.getStatus()) ;
    }

    @ExceptionHandler(BilletAnnulationImpossible.class)
    public ResponseEntity<String> gererBilletAnnulationImpossible (HttpServletRequest requete, BilletAnnulationImpossible ex) {
        return new ResponseEntity<>("Impossible d'annuler ce billet", HttpStatus.BAD_REQUEST) ;
    }

    @ExceptionHandler(TooManyBilletsException.class)
    public ResponseEntity<String> gererTooManyBilletsException(HttpServletRequest request, TooManyBilletsException ex){
        return new ResponseEntity<>("Ce spectateur possède trop de billet", ex.getStatus());
    }

    @ExceptionHandler(ExisteBillets.class)
    public ResponseEntity<String> gererExisteBillets(HttpServletRequest request, ExisteBillets ex){
        return new ResponseEntity<>("Ce spectateur possède trop de billet", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BilletAchatImpossible.class)
    public ResponseEntity<String> gererBilletAchatImpossible(HttpServletRequest request, BilletAchatImpossible ex){
        return new ResponseEntity<>("L'achat de ce billet n'est pas autorisé", ex.getStatus());
    }
}
