package com.projetae.miagiques.utilities;

import com.projetae.miagiques.utilities.EpreuveExceptions.CapaciteEpreuveSuperieur;
import com.projetae.miagiques.utilities.EpreuveExceptions.EpreuveExiste;
import com.projetae.miagiques.utilities.EpreuveExceptions.EpreuveInexistante;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TraitementExceptionsEpreuve {
    @ExceptionHandler(EpreuveExiste.class)
    public ResponseEntity<String> gererEpreuveExiste (HttpServletRequest requete, EpreuveExiste ex) {
        return new ResponseEntity<>("Cette épreuve existe déjà (le nom est déjà pris)", HttpStatus.CONFLICT) ;
    }

    @ExceptionHandler(CapaciteEpreuveSuperieur.class)
    public ResponseEntity<String> gererCapaciteSuperieur (HttpServletRequest requete, CapaciteEpreuveSuperieur ex) {
        return new ResponseEntity<>("Capacité non conforme de l'épreuve pour l'infrastructure correspondante", HttpStatus.BAD_REQUEST) ;
    }

    @ExceptionHandler(EpreuveInexistante.class)
    public ResponseEntity<String> gererEpreuveInexistante(HttpServletRequest requete, EpreuveInexistante ex) {
        return new ResponseEntity<>("Epreuve non trouvé dans la base", HttpStatus.NOT_FOUND) ;
    }



}
