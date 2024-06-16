package com.projetae.miagiques.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ParticipantDTO {
    private String nom;
    private String prenom;
    private String email;
}
