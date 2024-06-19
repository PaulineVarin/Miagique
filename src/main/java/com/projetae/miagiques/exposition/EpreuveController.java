package com.projetae.miagiques.exposition;

import com.projetae.miagiques.entities.Epreuve;
import com.projetae.miagiques.dto.EpreuveDTO;
import com.projetae.miagiques.entities.Organisateur;
import com.projetae.miagiques.entities.Participant;
import com.projetae.miagiques.entities.Personne;
import com.projetae.miagiques.metier.EpreuveService;
import com.projetae.miagiques.metier.PersonneService;
import com.projetae.miagiques.utilities.EpreuveExceptions.CapaciteEpreuveSuperieur;
import com.projetae.miagiques.utilities.EpreuveExceptions.EpreuveExiste;
import com.projetae.miagiques.utilities.EpreuveExceptions.EpreuveInexistante;
import com.projetae.miagiques.utilities.InfrastructureSportiveExceptions.InfrastructureSportiveInexistante;
import com.projetae.miagiques.utilities.PersonneExceptions.CompteInexistant;
import com.projetae.miagiques.utilities.PersonneExceptions.RoleIncorrect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("/epreuve/{emailUtilisateur}")
public class EpreuveController {

    @Autowired
    private EpreuveService epreuveService;

    @Autowired
    private PersonneService personneService;
    
    /**
     *
     * @param mail représente le mail de la personne qui essaye de faire une action
     * @param type représente la classe a laquelle devra appartenir l'utilisateur
     * @return true si l'action est valide avec le compte mentionné
     * @throws CompteInexistant si le compte avec l'adresse email mentionné n'existe pas
     * @throws RoleIncorrect si le role ne correspond pas a la tache qu'on essaye de faire
     */
    public <T> boolean testerRole(String mail, Class<T> type) throws CompteInexistant, RoleIncorrect {
        ArrayList<Personne> listeRolesPersonne = new ArrayList<>(this.personneService.seConnecter(mail)) ;

        if (listeRolesPersonne.stream().noneMatch(p -> type.isInstance(p))){
            throw new RoleIncorrect();
        }
        return true ;
    }

    @GetMapping("/consulterEpreuvesDisponibles")
    public Collection<EpreuveDTO> consulterEpreuvesDisponibles(@PathVariable("emailUtilisateur") String email) throws RoleIncorrect, CompteInexistant {
        this.testerRole(email,Participant.class) ;
        return this.epreuveService.getAllEpreuvesDisponibles() ;
    }

    /**
     *
     * @param email représente le mail de la personne qui essaye de faire une action
     * @return l'ensemble des epreuves de la base
     * @throws RoleIncorrect
     * @throws CompteInexistant
     */
    @GetMapping({"/consulterEpreuves"})
    public Collection<EpreuveDTO> consulterEpreuves(@PathVariable("emailUtilisateur") String email) throws RoleIncorrect, CompteInexistant {
        this.testerRole(email,Organisateur.class) ;
        return this.epreuveService.consulterEpreuves() ;
    }


    /**
     * @param epreuveInfos objet EpreuveDTO qui contient les informations de base d'une épreuve -
     *                     Doit posséder au minimum les attributs suivants initialisés "nom" ; "date" ; "nbPlacesSpectateur" ; "nbParticipants" ; "idInfrastructure"
     * @param email représente le mail de la personne qui essaye de faire une action
     * @return l'épreuve nouvellement ajouté
     * @throws EpreuveExiste si l'on cherche à créer une épreuve avec le même nom qu'une épreuve déjà en base
     * @throws InfrastructureSportiveInexistante si l'on cherche à lié une épreuve avec une infrastructure qui n'existe pas
     * @throws CapaciteEpreuveSuperieur si le nombre de spectateurs prévues est supérieur à la capacité de l'infrastructure
     * @throws ParseException
     */
    @PostMapping("/creationEpreuve")
    public EpreuveDTO creationEpreuve(@PathVariable("emailUtilisateur") String email, @RequestBody EpreuveDTO epreuveInfos) throws EpreuveExiste, InfrastructureSportiveInexistante, CapaciteEpreuveSuperieur, RoleIncorrect, CompteInexistant {
        this.testerRole(email,Organisateur.class) ;
        return this.epreuveService.creationEpreuve(epreuveInfos) ;
    }

    /**
     * @param email représente le mail de la personne qui essaye de faire une action
     * @param epreuveInfos objet EpreuveDTO qui contient les informations de base d'une épreuve -
     *                     Doit posséder au minimum les attributs suivants initialisés "nom" ; "date" ; "nbPlacesSpectateur" ; "nbParticipants" ; "idInfrastructure"
     * @param idE représente l'id de l'épreuve que l'on cherche à modifier
     * @return l'épreuve modifié
     * @throws EpreuveInexistante si l'on cherche à modifier une épreuve qui n'existe pas
     * @throws InfrastructureSportiveInexistante si l'on cherche à lié une épreuve avec une infrastructure qui n'existe pas
     * @throws CapaciteEpreuveSuperieur si le nombre de spectateurs prévues est supérieur à la capacité de l'infrastructure
     * @throws ParseException
     */
    @PutMapping("/miseAjour/{idEpreuve}")
    public EpreuveDTO miseAJourEpreuve(@PathVariable("emailUtilisateur") String email, @RequestBody EpreuveDTO epreuveInfos, @PathVariable("idEpreuve") Long idE) throws EpreuveInexistante, InfrastructureSportiveInexistante, CapaciteEpreuveSuperieur, RoleIncorrect, CompteInexistant, EpreuveExiste {
        this.testerRole(email,Organisateur.class) ;
        return this.epreuveService.miseAJourEpreuve(epreuveInfos, idE) ;
    }

    /**
     * @param email représente le mail de la personne qui essaye de faire une action
     * @param idEpreuve représente l'id de l'épreuve que l'on cherche à supprimer
     * @return un message explicitant la bonne réussite de l'opération
     * @throws EpreuveInexistante si l'on cherche à supprimer une épreuve qui n'existe pas
     */
    @DeleteMapping("/suppressionEpreuve/{idEpreuve}")
    public ResponseEntity<String> supprimerEpreuve(@PathVariable("emailUtilisateur") String email, @PathVariable("idEpreuve") Long idEpreuve) throws EpreuveInexistante, RoleIncorrect, CompteInexistant {
        this.testerRole(email,Organisateur.class) ;
        return this.epreuveService.supprimerEpreuve(idEpreuve) ;
    }
}
