package com.projetae.miagiques.exposition;

import com.projetae.miagiques.entities.Controleur;
import com.projetae.miagiques.entities.Delegation;
import com.projetae.miagiques.entities.Organisateur;
import com.projetae.miagiques.entities.Personne;
import com.projetae.miagiques.metier.DelegationService;
import com.projetae.miagiques.metier.PersonneService;
import com.projetae.miagiques.utilities.DelegationExceptions.DelegationAvecParticipant;
import com.projetae.miagiques.utilities.DelegationExceptions.DelegationExistante;
import com.projetae.miagiques.utilities.DelegationExceptions.DelegationInexistante;
import com.projetae.miagiques.utilities.PersonneExceptions.CompteInexistant;
import com.projetae.miagiques.utilities.PersonneExceptions.RoleIncorrect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/delegation/{emailUtilisateur}")
public class DelegationController {

    @Autowired
    private DelegationService delegationService;

    @Autowired
    private PersonneService personneService ;

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

    @PostMapping("/creerDelegation")
    public Delegation creerDelegation(@PathVariable("emailUtilisateur") String email, @RequestBody String nom) throws DelegationExistante, RoleIncorrect, CompteInexistant {
        this.testerRole(email, Organisateur.class) ;
        return delegationService.creerDelegation(nom);
    }

    @DeleteMapping("/supprimerDelegation")
    public ResponseEntity<String> supprimerDelegation(@PathVariable("emailUtilisateur") String email, @RequestBody Long idD) throws DelegationInexistante, RoleIncorrect, CompteInexistant, DelegationAvecParticipant {
        this.testerRole(email, Organisateur.class) ;
        return this.delegationService.supprimerDelegation(idD);
    }

}
