package com.projetae.miagiques.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.projetae.miagiques.dto.EpreuveDTO;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
public class Epreuve {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idEpreuve;

    private String nom ;

    private Timestamp date ;

    private int nbPlacesSpectateur ;

    private int nbParticipants ;

    @OneToOne
    private StatistiqueEpreuve statistique;

    @OneToMany(mappedBy = "epreuve")
    private Collection<Billet> listeBillets ;

    @OneToMany(mappedBy = "epreuve")
    private Collection<Resultat> listeResultats ;

    @ManyToMany
    private Collection<Participant> participants ;

    @ManyToOne
    private InfrastructureSportive insfrastructureSportive ;

    public Epreuve(String nomE, Timestamp dateE, int nbPlacesSpectateurE, int nbParticipantsE, InfrastructureSportive infrastructureSportiveE) {
        this.nom = nomE ;
        this.date = dateE ;
        this.nbPlacesSpectateur = nbPlacesSpectateurE;
        this.nbParticipants = nbParticipantsE ;
        this.insfrastructureSportive = infrastructureSportiveE ;
    }

    public void updateEpreuve(EpreuveDTO epUpdate, InfrastructureSportive i) {
        this.setNom(epUpdate.getNom());
        this.setDate(epUpdate.getDate());
        this.setNbPlacesSpectateur(epUpdate.getNbPlacesSpectateur());
        this.setNbParticipants(epUpdate.getNbParticipants());
        this.setInsfrastructureSportive(i);
    }







}
