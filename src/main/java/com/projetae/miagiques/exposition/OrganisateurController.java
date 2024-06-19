package com.projetae.miagiques.exposition;


import com.projetae.miagiques.dto.OrganisateurDTO;
import com.projetae.miagiques.entities.Organisateur;
import com.projetae.miagiques.entities.Personne;
import com.projetae.miagiques.metier.OrganisateurService;
import com.projetae.miagiques.metier.PersonneService;
import com.projetae.miagiques.utilities.PersonneExceptions.CompteExiste;
import com.projetae.miagiques.utilities.PersonneExceptions.CompteInexistant;
import com.projetae.miagiques.utilities.PersonneExceptions.RoleIncorrect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;



@RestController
@RequestMapping("/organisateur")
public class OrganisateurController {

    @Autowired
    private PersonneService personneService ;

    @Autowired
    private OrganisateurService organisateurService ;
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
     * @param organisateurInfos informations concernant un organisateur
     * @return l'organisateur créer
     * @throws CompteExiste si un compte avec le même mail existe déjà
     */
    @PostMapping("/creationOrganisateur")
    public OrganisateurDTO creationOrganisateur(@RequestBody OrganisateurDTO organisateurInfos) throws CompteExiste {
        return this.organisateurService.creationOrganisateur(organisateurInfos) ;
    }

    @DeleteMapping("/{emailUtilisateur}/suppressionOrganisateur/{idOrganisateur}")
    public ResponseEntity<String> supprimerOrganisateur(@PathVariable("emailUtilisateur") String email, @PathVariable("idOrganisateur") Long idOrganisateur) throws RoleIncorrect, CompteInexistant {
        this.testerRole(email,Organisateur.class) ;
        return this.organisateurService.supprimerOrganisateur(idOrganisateur);
    }
}
