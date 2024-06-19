package com.projetae.miagiques.exposition;

import com.projetae.miagiques.entities.Billet;
import com.projetae.miagiques.entities.Personne;
import com.projetae.miagiques.entities.Spectateur;
import com.projetae.miagiques.metier.PersonneService;
import com.projetae.miagiques.metier.SpectateurService;
import com.projetae.miagiques.utilities.BilletExceptions.BilletAnnulationImpossible;
import com.projetae.miagiques.utilities.BilletExceptions.BilletInexistant;
import com.projetae.miagiques.utilities.PersonneExceptions.CompteInexistant;
import com.projetae.miagiques.utilities.PersonneExceptions.RoleIncorrect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("/spectateur")
public class SpectateurController {
    @Autowired
    private SpectateurService spectateurService ;
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
     * @param idSpectateur représente l'id d'un spectateur
     * @return la liste des billets du spectateur
     */
    @GetMapping({"/{id_spectateur}/listerBillets"})
    public Collection<Billet> getAllBillets(@PathVariable("emailUtilisateur") String email, @PathVariable("id_spectateur") Long idSpectateur) throws RoleIncorrect, CompteInexistant {
        this.testerRole(email, Spectateur.class) ;
        return this.spectateurService.getAllBillets(idSpectateur) ;
    }

    /**
     *
     * @param idBillet représente l'id d'un billet
     * @param idSpectateur représente l'id d'un spectateur
     * @return un message contenant le montant qui sera remboursé
     * @throws BilletInexistant si l'on cherche à annuler un billet qui n'existe pas
     * @throws BilletAnnulationImpossible si l'on ne peut pas annuler le billet
     */
    @PutMapping("/{id_spectateur}/annulerBillet/{id_billet}")
    public String annulerBillet(@PathVariable("emailUtilisateur") String email, @PathVariable("id_billet") Long idBillet, @PathVariable("id_spectateur") Long idSpectateur) throws BilletInexistant, BilletAnnulationImpossible, RoleIncorrect, CompteInexistant {
        this.testerRole(email, Spectateur.class) ;
        return this.spectateurService.annulerBillet(idBillet, idSpectateur);
    }
}
