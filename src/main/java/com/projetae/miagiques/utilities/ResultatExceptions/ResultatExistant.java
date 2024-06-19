package com.projetae.miagiques.utilities.ResultatExceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class ResultatExistant extends Exception {

    private HttpStatus httpStatus;
}
