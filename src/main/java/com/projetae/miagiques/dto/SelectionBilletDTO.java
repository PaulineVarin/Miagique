package com.projetae.miagiques.dto;

import com.projetae.miagiques.entities.Billet;

public class SelectionBilletDTO {

    private float prixBillet;
    private Billet billet;

    public SelectionBilletDTO(float prixBillet, Billet billet){
        this.prixBillet = prixBillet;
        this.billet = billet;
    }
}
