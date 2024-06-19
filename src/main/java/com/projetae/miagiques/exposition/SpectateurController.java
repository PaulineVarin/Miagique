package com.projetae.miagiques.exposition;

import com.projetae.miagiques.dto.SpectateurDTO;
import com.projetae.miagiques.entities.Organisateur;
import com.projetae.miagiques.entities.Personne;
import com.projetae.miagiques.metier.PersonneService;
import com.projetae.miagiques.metier.SpectateurService;
import com.projetae.miagiques.utilities.PersonneExceptions.CompteExiste;
import com.projetae.miagiques.utilities.PersonneExceptions.CompteInexistant;
import com.projetae.miagiques.utilities.PersonneExceptions.RoleIncorrect;
import com.projetae.miagiques.utilities.BilletExceptions.ExisteBillets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

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


    @PostMapping("/creationSpectateur")
    public SpectateurDTO creerSpectateur(@RequestBody SpectateurDTO spectateurInfos) throws CompteExiste {
        return this.spectateurService.creerSpectateur(spectateurInfos);
    }


    @DeleteMapping("/{emailUtilisateur}/suppressionSpectateur/{idSpectateur}")
    public ResponseEntity<String> supprimerSpectateur(@PathVariable("emailUtilisateur") String email, @PathVariable("idSpectateur") Long idSpectateur) throws RoleIncorrect, CompteInexistant, ExisteBillets {
        this.testerRole(email, Organisateur.class) ;
        return this.spectateurService.supprimerSpectateur(idSpectateur);
    }

}
