package com.projetae.miagiques.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ControleurDTO {

    private Long id;
    private String nom;
    private String prenom;
    private String email;
}
