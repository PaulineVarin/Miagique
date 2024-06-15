package com.projetae.miagiques.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EpreuveDTO {
    private Long idEpreuve;

    private String nom ;

    private Timestamp date ;

    private int nbPlacesSpectateur ;

    private int nbParticipants ;

}
