package com.projetae.miagiques.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InfrastructureSportiveDTO {
    private Long idInfrastructure;

    private String nom ;

    private int capacite ;

    private String adresse ;
}
