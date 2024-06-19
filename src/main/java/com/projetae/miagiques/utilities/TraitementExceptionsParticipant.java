package com.projetae.miagiques.utilities;

import com.projetae.miagiques.utilities.BilletExceptions.BilletInexistant;
import com.projetae.miagiques.utilities.ParticipantExceptions.ParticipantExistant;
import com.projetae.miagiques.utilities.ParticipantExceptions.ParticipantInexistant;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TraitementExceptionsParticipant {
    @ExceptionHandler(ParticipantExistant.class)
    public ResponseEntity<String> gererParticipantExistant(HttpServletRequest requete, ParticipantExistant ex) {
        return new ResponseEntity<>("Ce participant existe déjà. Le mail est déjà associé à un participant.", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ParticipantInexistant.class)
    public ResponseEntity<String> gererParticipantInexistant(HttpServletRequest requete, ParticipantInexistant ex) {
        return new ResponseEntity<>("Le participant est inexistant.", HttpStatus.NOT_FOUND);
    }

}
