package com.projetae.miagiques.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResultatInfosDTO {

    private float temps;
    private int points;
    private int position;
    private Long idEpreuve;
    private Long idParticipant;
}
