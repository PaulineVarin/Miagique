package com.projetae.miagiques.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
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
    private ArrayList<Epreuve> listeEpreuves ;

}
