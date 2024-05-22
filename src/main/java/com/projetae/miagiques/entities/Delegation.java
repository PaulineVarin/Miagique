package com.projetae.miagiques.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

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
    private Set<Participant> participants;

}
