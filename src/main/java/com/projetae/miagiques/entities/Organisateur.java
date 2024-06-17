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
public class Organisateur extends Personne {
    public Organisateur(String email, String nom, String prenom) {
        super();
        this.setEmail(email);
        this.setNom(nom);
        this.setPrenom(prenom);
    }
}
