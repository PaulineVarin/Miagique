package com.projetae.miagiques.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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

}
