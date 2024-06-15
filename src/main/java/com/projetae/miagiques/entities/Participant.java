package com.projetae.miagiques.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
public class Participant extends Personne {

    @OneToMany(mappedBy = "participant")
    private Collection<Resultat> resultats;

    @ManyToOne
    private Delegation delegation;

    @ManyToMany
    private Collection<Epreuve> epreuves;
}
