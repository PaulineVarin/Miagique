package com.projetae.miagiques.entities;

import com.projetae.miagiques.utilities.StatutBillet;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

@Entity
public class Billet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idBillet;

    private float prix;

    @Enumerated(EnumType.STRING)
    private StatutBillet etat ;

    @ManyToOne
    private Spectateur spectateur ;

    @ManyToOne
    private Epreuve epreuve ;

    public Billet(float prix, StatutBillet etat, Spectateur spectateur, Epreuve epreuve){
        this.prix = prix;
        this.etat = etat;
        this.spectateur = spectateur;
        this.epreuve = epreuve;
    }
}
