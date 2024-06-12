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

    /**
     *
     * @param epreuveInfos JSON qui contient les informations de base d'une épreuve -
     *                     Doit posséder les attributs suivants "nom" ; "date" ; "nbPlacesSpectateur" ; "nbParticipants" ; "idInfrastructure"
     * @return l'épreuve nouvellement ajouté
     * @throws EpreuveExiste si l'on cherche à créer une épreuve avec le même nom qu'une épreuve déjà en base
     * @throws InfrastructureSportiveInexistant si l'on cherche à lié une épreuve avec une infrastructure qui n'existe pas
     * @throws CapaciteEpreuveSuperieur si le nombre de spectateurs prévues est supérieur à la capacité de l'infrastructure
     * @throws ParseException
     */
    @PostMapping("/epreuves")
    public Epreuve creationEpreuve(@RequestBody Map<String, Object> epreuveInfos) throws EpreuveExiste, InfrastructureSportiveInexistant, CapaciteEpreuveSuperieur, ParseException {
        return this.organisateurService.creationEpreuve(epreuveInfos) ;
    }

    /**
     *
     * @param epreuveInfos JSON qui contient les informations de base d'une épreuve -
     *                     Doit posséder les attributs suivants "nom" ; "date" ; "nbPlacesSpectateur" ; "nbParticipants" ; "idInfrastructure"
     * @param idE représente l'id de l'épreuve que l'on cherche à modifier
     * @return l'épreuve modifié
     * @throws EpreuveInexistante si l'on cherche à modifier une épreuve qui n'existe pas
     * @throws InfrastructureSportiveInexistant si l'on cherche à lié une épreuve avec une infrastructure qui n'existe pas
     * @throws CapaciteEpreuveSuperieur si le nombre de spectateurs prévues est supérieur à la capacité de l'infrastructure
     * @throws ParseException
     */
    @PutMapping("/epreuve/{idEpreuve}/miseAjour")
    public Epreuve miseAJourEpreuve(@RequestBody Map<String, Object> epreuveInfos, @PathVariable("idEpreuve") Long idE) throws EpreuveInexistante, InfrastructureSportiveInexistant, CapaciteEpreuveSuperieur, ParseException {
        return this.organisateurService.miseAJourEpreuve(epreuveInfos, idE) ;
    }



}
