package com.projetae.miagiques.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
