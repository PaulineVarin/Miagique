package com.projetae.miagiques.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
public class Epreuve {

    @Id
    private Long idEpreuve;

    private String nom ;

    private Timestamp date ;

    private int nbPlacesSpectateur ;

    private int nbParticipants ;

    @OneToOne
    private StatistiqueEpreuve statistique;
    @OneToMany(mappedBy = "epreuve")
    private ArrayList<Billet> listeBillets ;

    @OneToMany(mappedBy = "epreuve")
    private ArrayList<Resultat> listeResultats ;

    @ManyToOne
    private Participant participant ;

    @ManyToOne
    private InfrastructureSportive insfrastructureSportive ;



}
