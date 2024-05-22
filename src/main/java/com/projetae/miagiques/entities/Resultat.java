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
public class Resultat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idResultat;

    private float temps;

    private int points;

    private int position;

    private boolean isForfait;

    @ManyToOne
    private Epreuve epreuve;

    @ManyToOne
    private Participant participant;


}
