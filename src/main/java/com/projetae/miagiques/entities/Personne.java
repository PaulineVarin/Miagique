/*
 * PersonneController.java                                                22/05/2024
 *
 * @author Morgan Nayet
 */

package com.projetae.miagiques.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * Classe abstraite personne sauvegardant les données liées aux différents acteurs
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public abstract class Personne {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nom;
    private String prenom;
    private String email;

    public Personne(String nom, String prenom, String email) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
    }
}
