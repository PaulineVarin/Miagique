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

    private StatutBillet etat ;

    @ManyToOne
    private Spectateur spectateur ;


    private Epreuve epreuve ;
}
