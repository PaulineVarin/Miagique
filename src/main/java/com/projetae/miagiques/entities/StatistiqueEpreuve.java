package com.projetae.miagiques.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
public class StatistiqueEpreuve {

    @Id
    private Long idStatistiqueEpreuve;

    @OneToOne
    private Epreuve epreuve;

    private int nbPlacesRestantes;

    private int nbPlaces;



}
