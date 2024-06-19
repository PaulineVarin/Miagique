package com.projetae.miagiques.dto;

import com.projetae.miagiques.entities.InfrastructureSportive;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EpreuveDTO {
    private String nom ;

    private Timestamp date ;

    private int nbPlacesSpectateur ;

    private int nbParticipants ;

    private Long infrastructureSportiveId ;

}
