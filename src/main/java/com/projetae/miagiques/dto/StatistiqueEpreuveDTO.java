package com.projetae.miagiques.dto;

import com.projetae.miagiques.entities.Epreuve;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StatistiqueEpreuveDTO {
    private EpreuveDTO epreuve;
    private int nbPlacesRestantes;
    private int nbPlaces;
}
