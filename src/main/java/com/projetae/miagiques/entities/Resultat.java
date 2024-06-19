package com.projetae.miagiques.entities;

import jakarta.persistence.*;
import jakarta.servlet.http.Part;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
public class Resultat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idResultat;

    private float temps;

    private int points;

    private int position;

    private boolean isForfait;

    @ManyToOne
    private Epreuve epreuve;

    @ManyToOne
    private Participant participant;

    /**
     * A utiliser si un des participants déclare forfait
     * @param isForfait
     */
    public Resultat(boolean isForfait) {
        this.temps = 0;
        this.points = 0;
        this.position = 0;
        this.isForfait = isForfait;
    }

    /**
     * A utiliser à la publication des résultats
     * @param temps
     * @param points
     * @param position
     * @param participant
     * @param epreuve
     */
    public Resultat(float temps, int points, int position, Participant participant, Epreuve epreuve){
        this.temps = temps;
        this.points = points;
        this.position = position;
        this.isForfait = false;
        this.epreuve = epreuve;
        this.participant = participant;
    }
}
