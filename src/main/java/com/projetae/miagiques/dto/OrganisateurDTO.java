package com.projetae.miagiques.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class OrganisateurDTO {

    private Long id;
    private String nom;
    private String prenom;
    private String email;
}
