package com.projetae.miagiques.exposition;

import com.projetae.miagiques.dto.InfrastructureSportiveDTO;
import com.projetae.miagiques.entities.Organisateur;
import com.projetae.miagiques.entities.Personne;
import com.projetae.miagiques.metier.InfrastructureSportiveService;
import com.projetae.miagiques.metier.PersonneService;
import com.projetae.miagiques.utilities.InfrastructureSportiveExceptions.InfrastructureSportiveExiste;
import com.projetae.miagiques.utilities.PersonneExceptions.CompteInexistant;
import com.projetae.miagiques.utilities.PersonneExceptions.RoleIncorrect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/infrastructure/{emailUtilisateur}")
public class InfrastructureSportiveController {
    @Autowired
    private  PersonneService personneService ;

   @Autowired
    private InfrastructureSportiveService infrastructureSportiveService ;


    /**
     *
     * @param mail représente le mail de la personne qui essaye de faire une action
     * @return true si l'action est valide avec le compte mentionné
     * @throws CompteInexistant si le compte avec l'adresse email mentionné n'existe pas
     * @throws RoleIncorrect si le role ne correspond pas a la tache qu'on essaye de faire
     */
    public boolean testerRole(String mail) throws CompteInexistant, RoleIncorrect {
        ArrayList<Personne> listeRolesPersonne = new ArrayList<>(this.personneService.seConnecter(mail)) ;

        if (listeRolesPersonne.stream().noneMatch(p -> p instanceof Organisateur)){
            throw new RoleIncorrect();
        }
        return true ;
    }

    @PostMapping("/creationInfrastructure")
    public InfrastructureSportiveDTO creationInfrastructure(@PathVariable("emailUtilisateur") String email, @RequestBody InfrastructureSportiveDTO infrastructureInfos) throws RoleIncorrect, CompteInexistant, InfrastructureSportiveExiste {
        this.testerRole(email) ;
        return this.infrastructureSportiveService.creationInfrastructure(infrastructureInfos) ;
    }
}
