package com.projetae.miagiques.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Participant extends Personne {

    @OneToMany(mappedBy = "participant")
    private ArrayList<Resultat> resultats;

    @ManyToOne
    private Delegation delegation;
}
