package com.projetae.miagiques.utilities;

import com.projetae.miagiques.utilities.PersonneExceptions.CompteExiste;
import com.projetae.miagiques.utilities.PersonneExceptions.CompteInexistant;
import com.projetae.miagiques.utilities.PersonneExceptions.RoleIncorrect;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TraitementExceptionsPersonne {
    @ExceptionHandler(CompteInexistant.class)
    public ResponseEntity<String> gererCompteInexistant(HttpServletRequest requete, CompteInexistant ex) {
        return new ResponseEntity<>("Utilisateur inconnu", HttpStatus.NOT_FOUND) ;
    }

    @ExceptionHandler(RoleIncorrect.class)
    public ResponseEntity<String> gererRoleIncorrect(HttpServletRequest requete, RoleIncorrect ex) {
        return new ResponseEntity<>("Role non suffisant pour faire cette opération", HttpStatus.BAD_REQUEST) ;
    }

    @ExceptionHandler(CompteExiste.class)
    public ResponseEntity<String> gererCompteExiste(HttpServletRequest requete, CompteExiste ex) {
        return new ResponseEntity<>("Email déjà présent pour ce type de compte", HttpStatus.BAD_REQUEST) ;
    }


}
