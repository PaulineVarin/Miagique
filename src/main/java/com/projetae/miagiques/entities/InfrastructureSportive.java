package com.projetae.miagiques.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.projetae.miagiques.dto.InfrastructureSportiveDTO;
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
public class InfrastructureSportive {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idInfrastructure;

    private String nom ;

    private int capacite ;

    private String adresse ;

    @OneToMany(mappedBy = "insfrastructureSportive")
    private Collection<Epreuve> listeEpreuves ;

    public InfrastructureSportive(String nom, int capacite, String adresse) {
        this.nom = nom;
        this.capacite = capacite ;
        this.adresse = adresse ;
    }

    public void updateInfrastructure(InfrastructureSportiveDTO infrastructureUpdate) {
        this.nom = infrastructureUpdate.getNom();
        this.capacite = infrastructureUpdate.getCapacite() ;
        this.adresse = infrastructureUpdate.getAdresse() ;
    }
}
