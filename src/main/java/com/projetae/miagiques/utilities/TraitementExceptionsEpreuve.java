package com.projetae.miagiques.utilities;

import com.projetae.miagiques.utilities.BilletExceptions.BilletInexistant;
import com.projetae.miagiques.utilities.EpreuveExceptions.CapaciteEpreuveSuperieur;
import com.projetae.miagiques.utilities.EpreuveExceptions.EpreuveExiste;
import com.projetae.miagiques.utilities.EpreuveExceptions.EpreuveInexistante;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.text.ParseException;

@ControllerAdvice
public class TraitementExceptionsEpreuve {
    @ExceptionHandler(EpreuveExiste.class)
    public ResponseEntity<String> gererEpreuveExiste (HttpServletRequest requete, EpreuveExiste ex) {
        return new ResponseEntity<>("Cette épreuve existe déjà", HttpStatus.CONFLICT) ;
    }

    @ExceptionHandler(CapaciteEpreuveSuperieur.class)
    public ResponseEntity<String> gererCapaciteSuperieur (HttpServletRequest requete, CapaciteEpreuveSuperieur ex) {
        return new ResponseEntity<>("Capacité non conforme de l'épreuve pour l'infrastructure correspondante", HttpStatus.BAD_REQUEST) ;
    }

    @ExceptionHandler(ParseException.class)
    public ResponseEntity<String> gererErreurParse(HttpServletRequest requete, ParseException ex) {
        return new ResponseEntity<>("Erreur de parsing au niveau de la date de l'épreuve. Merci de respecter le format dd-mm-yyyy", HttpStatus.BAD_REQUEST) ;
    }

    @ExceptionHandler(EpreuveInexistante.class)
    public ResponseEntity<String> gererEpreuveInexistante(HttpServletRequest requete, EpreuveInexistante ex) {
        return new ResponseEntity<>("Epreuve non trouvé dans la base", HttpStatus.CONFLICT) ;
    }



}