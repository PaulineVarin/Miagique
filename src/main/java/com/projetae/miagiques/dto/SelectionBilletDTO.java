package com.projetae.miagiques.dto;

import com.projetae.miagiques.entities.Billet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SelectionBilletDTO {

    private float prixBillet;
    private Long idBillet;

}
