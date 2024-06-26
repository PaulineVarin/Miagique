package com.projetae.miagiques.entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Controleur extends Personne {
    public Controleur(String email, String nom, String prenom) {
        super(nom, prenom, email) ;
    }
}
