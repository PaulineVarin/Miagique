package com.projetae.miagiques.utilities.ParticipantExceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ParticipantInexistant extends Exception{

    private HttpStatus httpStatus;
}
