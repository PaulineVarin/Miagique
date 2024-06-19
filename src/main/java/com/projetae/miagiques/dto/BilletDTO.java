package com.projetae.miagiques.dto;

import com.projetae.miagiques.entities.Epreuve;
import com.projetae.miagiques.entities.Spectateur;
import com.projetae.miagiques.utilities.StatutBillet;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BilletDTO {
    private Long idBillet;

    private float prix;

    private StatutBillet etat ;


    private Long spectateurId ;


    private Long epreuveId ;
}
