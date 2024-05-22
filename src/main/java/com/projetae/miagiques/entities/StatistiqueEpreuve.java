package com.projetae.miagiques.entities;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idStatistiqueEpreuve;

    @OneToOne
    private Epreuve epreuve;

    private int nbPlacesRestantes;

    private int nbPlaces;



}
