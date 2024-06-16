package com.projetae.miagiques.exposition;

import com.projetae.miagiques.dto.EpreuveDTO;
import com.projetae.miagiques.entities.Billet;
import com.projetae.miagiques.entities.Epreuve;
import com.projetae.miagiques.metier.OrganisateurService;
import com.projetae.miagiques.utilities.EpreuveExceptions.CapaciteEpreuveSuperieur;
import com.projetae.miagiques.utilities.EpreuveExceptions.EpreuveExiste;
import com.projetae.miagiques.utilities.EpreuveExceptions.EpreuveInexistante;
import com.projetae.miagiques.utilities.InfrastructureSportiveExceptions.InfrastructureSportiveInexistante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.util.Collection;


@RestController
@RequestMapping("/organisateurs/{emailUtilisateur}")

public class OrganisateurController {

    @Autowired
    private OrganisateurService organisateurService ;


    @GetMapping({"/listerEpreuves"})
    public Collection<EpreuveDTO> getAllEpreuves() {
        return this.organisateurService.getAllEpreuves() ;
    }

    /**
     *
     * @param epreuveInfos objet EpreuveDTO qui contient les informations de base d'une épreuve -
     *                     Doit posséder au minimum les attributs suivants initialisés "nom" ; "date" ; "nbPlacesSpectateur" ; "nbParticipants" ; "idInfrastructure"
     * @return l'épreuve nouvellement ajouté
     * @throws EpreuveExiste si l'on cherche à créer une épreuve avec le même nom qu'une épreuve déjà en base
     * @throws InfrastructureSportiveInexistante si l'on cherche à lié une épreuve avec une infrastructure qui n'existe pas
     * @throws CapaciteEpreuveSuperieur si le nombre de spectateurs prévues est supérieur à la capacité de l'infrastructure
     * @throws ParseException
     */
    @PostMapping("/epreuves")
    public EpreuveDTO creationEpreuve(@RequestBody EpreuveDTO epreuveInfos) throws EpreuveExiste, InfrastructureSportiveInexistante, CapaciteEpreuveSuperieur {
        ///verifier que l'utilisateur existe et qu'il a le bon rôle
        return this.organisateurService.creationEpreuve(epreuveInfos) ;
    }

    /**
     *
     * @param epreuveInfos objet EpreuveDTO qui contient les informations de base d'une épreuve -
     *                     Doit posséder au minimum les attributs suivants initialisés "nom" ; "date" ; "nbPlacesSpectateur" ; "nbParticipants" ; "idInfrastructure"
     * @param idE représente l'id de l'épreuve que l'on cherche à modifier
     * @return l'épreuve modifié
     * @throws EpreuveInexistante si l'on cherche à modifier une épreuve qui n'existe pas
     * @throws InfrastructureSportiveInexistante si l'on cherche à lié une épreuve avec une infrastructure qui n'existe pas
     * @throws CapaciteEpreuveSuperieur si le nombre de spectateurs prévues est supérieur à la capacité de l'infrastructure
     * @throws ParseException
     */
    @PutMapping("/epreuve/{idEpreuve}/miseAjour")
    public EpreuveDTO miseAJourEpreuve(@RequestBody EpreuveDTO epreuveInfos, @PathVariable("idEpreuve") Long idE) throws EpreuveInexistante, InfrastructureSportiveInexistante, CapaciteEpreuveSuperieur {
        return this.organisateurService.miseAJourEpreuve(epreuveInfos, idE) ;
    }

    @DeleteMapping("/epreuves/{idEpreuve}")
    public ResponseEntity<String> supprimerEpreuve(@PathVariable("idEpreuve") Long idEpreuve) throws EpreuveInexistante {
        return this.organisateurService.supprimerEpreuve(idEpreuve) ;
    }



}
