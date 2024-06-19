package com.projetae.miagiques.exposition;


import com.projetae.miagiques.dto.EpreuveDTO;
import com.projetae.miagiques.dto.InfrastructureSportiveDTO;
import com.projetae.miagiques.entities.Organisateur;
import com.projetae.miagiques.entities.Personne;
import com.projetae.miagiques.metier.InfrastructureSportiveService;
import com.projetae.miagiques.metier.PersonneService;
import com.projetae.miagiques.utilities.InfrastructureSportiveExceptions.InfrastructureSportiveExiste;
import com.projetae.miagiques.utilities.InfrastructureSportiveExceptions.InfrastructureSportiveInexistante;
import com.projetae.miagiques.utilities.InfrastructureSportiveExceptions.InfrastructureSportiveReference;
import com.projetae.miagiques.utilities.PersonneExceptions.CompteInexistant;
import com.projetae.miagiques.utilities.PersonneExceptions.RoleIncorrect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;

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

    @GetMapping({"/listerInfrastructures"})
    public Collection<InfrastructureSportiveDTO> getAllInfrastructure(@PathVariable("emailUtilisateur") String email) throws RoleIncorrect, CompteInexistant {
        this.testerRole(email,Organisateur.class) ;
        return this.infrastructureSportiveService.getAllInfrastructures() ;
    }

    @PostMapping("/creationInfrastructure")
    public InfrastructureSportiveDTO creationInfrastructure(@PathVariable("emailUtilisateur") String email, @RequestBody InfrastructureSportiveDTO infrastructureInfos) throws RoleIncorrect, CompteInexistant, InfrastructureSportiveExiste {
        this.testerRole(email,Organisateur.class) ;
        return this.infrastructureSportiveService.creationInfrastructure(infrastructureInfos) ;
    }

    @PutMapping("/{idInfrastructure}/miseAjour")
    public InfrastructureSportiveDTO miseAJourInfrastructure(@PathVariable("emailUtilisateur") String email, @RequestBody InfrastructureSportiveDTO infrastructureInfos, @PathVariable("idInfrastructure") Long idI) throws RoleIncorrect, CompteInexistant, InfrastructureSportiveInexistante, InfrastructureSportiveExiste {
        this.testerRole(email,Organisateur.class) ;
        return this.infrastructureSportiveService.miseAJourInfrastructure(infrastructureInfos, idI) ;
    }

    @DeleteMapping("/{idInfrastructure}/suppression")
    public ResponseEntity<String> supprimerInfrastructure(@PathVariable("emailUtilisateur") String email, @PathVariable("idInfrastructure") Long idInfrastructure) throws RoleIncorrect, CompteInexistant, InfrastructureSportiveInexistante, InfrastructureSportiveReference {
        this.testerRole(email,Organisateur.class) ;
        return this.infrastructureSportiveService.supprimerInfrastructure(idInfrastructure) ;
    }
}
