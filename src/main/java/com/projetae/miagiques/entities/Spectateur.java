package com.projetae.miagiques.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Spectateur extends Personne {

    @OneToMany(mappedBy = "spectateur")
    private Collection<Billet> billets;

    public Spectateur(String email, String nom, String prenom) {
        super(nom, prenom, email);
    }
}
