package com.projetae.miagiques.utilities.DelegationExceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.net.http.HttpClient;

@AllArgsConstructor
@Getter
public class DelegationAvecParticipant extends Exception {

    private HttpStatus httpStatus;
}
