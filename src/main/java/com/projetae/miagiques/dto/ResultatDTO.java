package com.projetae.miagiques.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ResultatDTO {

    private Long idResultat;
    private float temps;
    private int points;
    private int position;
    private boolean isForfait;
    private Long idEpreuve;
    private String nomEpreuve;
    private Long idParticipant;
    private String nomParticipant;
}
