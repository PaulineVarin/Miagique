package com.projetae.miagiques.utilities;

import com.projetae.miagiques.utilities.PersonneExceptions.CompteInexistant;
import com.projetae.miagiques.utilities.ResultatExceptions.ResultatExistant;
import com.projetae.miagiques.utilities.ResultatExceptions.ResultatTempsNegatif;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TraitementExceptionsResultat {

    @ExceptionHandler(ResultatExistant.class)
    public ResponseEntity<String> gererResultatExistant(HttpServletRequest requete, ResultatExistant ex) {
        return new ResponseEntity<>("Résultat déjà existant", ex.getHttpStatus()) ;
    }

    @ExceptionHandler(ResultatTempsNegatif.class)
    public ResponseEntity<String> gererResultatTempsNegatif(HttpServletRequest requete, ResultatTempsNegatif ex) {
        return new ResponseEntity<>("Temps négatif non authorisé", ex.getHttpStatus()) ;
    }
}
