package com.projetae.miagiques.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.Collection;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
public class Delegation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idDelegation;

    private String nom;

    private int nbMedaillesOr;

    private int nbMedaillesArgent;

    private int nbMedaillesBronze;

    @OneToMany(mappedBy = "delegation")

    private Collection<Participant> participants;

    public Delegation(String nom) {
        this.nom = nom;
        this.nbMedaillesOr = 0;
        this.nbMedaillesArgent = 0;
        this.nbMedaillesBronze = 0;
        this.participants = new ArrayList<>();
    }

}
