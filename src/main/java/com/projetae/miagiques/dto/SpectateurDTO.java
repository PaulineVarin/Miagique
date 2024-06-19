package com.projetae.miagiques.dto;

import com.projetae.miagiques.entities.Billet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SpectateurDTO {

    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private Collection<Billet> billets;

}
