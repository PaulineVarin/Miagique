package com.projetae.miagiques.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;

@Entity
public class InfrastructureSportive {

    @Id
    private Long idInfrastructure;

    private String nom ;

    private int capacite ;

    private String adresse ;

    @OneToMany(mappedBy = "insfrastructureSportive")
    private ArrayList<Epreuve> listeEpreuves ;

}
