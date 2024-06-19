package com.projetae.miagiques.exposition;

import com.projetae.miagiques.dto.ParticipantDTO;
import com.projetae.miagiques.entities.Organisateur;
import com.projetae.miagiques.entities.Participant;
import com.projetae.miagiques.entities.Personne;
import com.projetae.miagiques.metier.ParticipantService;
import com.projetae.miagiques.metier.PersonneService;
import com.projetae.miagiques.utilities.DelegationExceptions.DelegationInexistante;
import com.projetae.miagiques.utilities.ParticipantExceptions.ParticipantExistant;
import com.projetae.miagiques.utilities.ParticipantExceptions.ParticipantInexistant;
import com.projetae.miagiques.utilities.PersonneExceptions.CompteInexistant;
import com.projetae.miagiques.utilities.PersonneExceptions.RoleIncorrect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/participant")
public class ParticipantController {
    @Autowired
    private ParticipantService participantService;
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


    /**
     *
     * @param email représente le mail de la personne qui essaye de faire une action
     * @param participantInfos JSON qui contient les informations d'un participant.
     *                         Il doit posséder les attributs suivants : nom, prenom, email, idDelegation
     * @throws DelegationInexistante Si on tente de créer un participant avec une délégation qui n'existe pas
     * @throws ParticipantExistant Si on essaie de créer un participant avec un email déjà utilisé
     * @return le participant ajouté
     */
    @PostMapping("/{emailUtilisateur}/creationParticipant")
    public ParticipantDTO creerParticipant(@PathVariable("emailUtilisateur") String email, @RequestBody Map<String, Object> participantInfos) throws DelegationInexistante, ParticipantExistant, RoleIncorrect, CompteInexistant {
        this.testerRole(email, Organisateur.class) ;
        return this.participantService.creerParticipant(participantInfos);
    }

    /**
     *
     * @param email représente le mail de la personne qui essaye de faire une action
     * @param idParticipant Id du participant qui doit être supprimé
     * @throws ParticipantInexistant si on tente de supprimer un participant qui n'existe pas
     * @throws RoleIncorrect si l'utilisateur n'a pas le bon rôle pour supprimer le participant
     * @throws CompteInexistant si le mail n'a pas de compte associé
     */
    @DeleteMapping("/{emailUtilisateur}/suppressionParticipant")
    public ResponseEntity<String> supprimerParticipant(@PathVariable("emailUtilisateur") String email, @RequestBody Long idParticipant) throws ParticipantInexistant, RoleIncorrect, CompteInexistant {
        this.testerRole(email, Organisateur.class) ;
        return this.participantService.supprimerParticipant(idParticipant);
    }

    /**
     *
     * @param email représente le mail de la personne qui essaye de faire une action
     * @param idEpreuve Id de l'épreuve dans lequel le participant veut s'inscrire
     * @param idParticipant Id du participant qui veut s'inscrire à une épreuve
     * @return Une confirmation de l'inscription sous forme de texte
     * @throws RoleIncorrect si l'utilisateur n'a pas le bon rôle pour s'inscrire à une épreuve
     * @throws CompteInexistant si le mail n'a pas de compte associé
     */
    @PostMapping("/{emailUtilisateur}/{idParticipant}/inscriptionEpreuve")
    public ResponseEntity<String> inscriptionEpreuve(@PathVariable("emailUtilisateur") String email, @RequestBody Long idEpreuve, @PathVariable Long idParticipant) throws RoleIncorrect, CompteInexistant {
        this.testerRole(email, Participant.class) ;
        return this.participantService.inscriptionEpreuve(idEpreuve, idParticipant);
    }

    @PostMapping("/{emailUtilisateur}/{idParticipant}/desengagementEpreuve")
    public ResponseEntity<String> desengagementEpreuve(@PathVariable("emailUtilisateur") String email, @RequestBody Long idEpreuve, @PathVariable Long idParticipant) throws RoleIncorrect, CompteInexistant {
        this.testerRole(email, Participant.class);
        return this.participantService.desengagementEpreuve(idEpreuve, idParticipant);
    }

}
