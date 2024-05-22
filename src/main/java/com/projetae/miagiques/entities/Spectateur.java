package com.projetae.miagiques.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;

@Entity
public class Spectateur extends Personne {
    @OneToMany(mappedBy = 'spectateur')
    private ArrayList<Billet> billets ;
}
