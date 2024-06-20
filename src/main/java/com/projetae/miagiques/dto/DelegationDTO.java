package com.projetae.miagiques.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DelegationDTO {
    private Long idDelegation;

    private String nom;

    private int nbMedaillesOr;

    private int nbMedaillesArgent;

    private int nbMedaillesBronze;

    private Collection<Long> idParticipants = new ArrayList<>() ;
}
