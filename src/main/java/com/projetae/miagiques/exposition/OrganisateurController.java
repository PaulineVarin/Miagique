package com.projetae.miagiques.exposition;

import com.projetae.miagiques.entities.Epreuve;
import com.projetae.miagiques.entities.InfrastructureSportive;
import com.projetae.miagiques.metier.OrganisateurService;
import com.projetae.miagiques.utilities.EpreuveExceptions.CapaciteEpreuveSuperieur;
import com.projetae.miagiques.utilities.EpreuveExceptions.EpreuveExiste;
import com.projetae.miagiques.utilities.EpreuveExceptions.EpreuveInexistante;
import com.projetae.miagiques.utilities.InfrastructureSportiveExceptions.InfrastructureSportiveInexistant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Map;

@RestController
@RequestMapping("/organisateurs")
public class OrganisateurController {

    @Autowired
    private OrganisateurService organisateurService ;

    @PostMapping("/epreuves")
    public Epreuve creationEpreuve(@RequestBody Map<String, Object> epreuveInfos) throws EpreuveExiste, InfrastructureSportiveInexistant, CapaciteEpreuveSuperieur, ParseException {
        return this.organisateurService.creationEpreuve(epreuveInfos) ;
    }

    @PutMapping("/epreuve/{idEpreuve}/miseAjour")
    public Epreuve miseAJourEpreuve(@RequestBody Map<String, Object> epreuveInfos, @PathVariable("idEpreuve") Long idE) throws EpreuveInexistante, InfrastructureSportiveInexistant, CapaciteEpreuveSuperieur, ParseException {
        return this.organisateurService.miseAJourEpreuve(epreuveInfos, idE) ;
    }



}
