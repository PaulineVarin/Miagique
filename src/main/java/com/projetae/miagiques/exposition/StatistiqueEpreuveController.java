package com.projetae.miagiques.exposition;

import com.projetae.miagiques.dto.StatistiqueEpreuveDTO;
import com.projetae.miagiques.entities.Organisateur;
import com.projetae.miagiques.entities.Personne;
import com.projetae.miagiques.metier.PersonneService;
import com.projetae.miagiques.metier.StatistiqueEpreuveService;
import com.projetae.miagiques.utilities.PersonneExceptions.CompteInexistant;
import com.projetae.miagiques.utilities.PersonneExceptions.RoleIncorrect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("/statistique/{emailUtilisateur}")
public class StatistiqueEpreuveController {

    @Autowired
    private PersonneService personneService;
    @Autowired
    private StatistiqueEpreuveService statistiqueEpreuveService;

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
     * @param email représente le mail de la personne qui essaye de faire une action
     * @return les statistiques de vente
     */
    @GetMapping("/statistiquesVentes")
    public Collection<StatistiqueEpreuveDTO> getStatistiquesVentes(@PathVariable("emailUtilisateur") String email) throws RoleIncorrect, CompteInexistant {
        this.testerRole(email, Organisateur.class) ;
        return this.statistiqueEpreuveService.getStatistiquesVentes() ;
    }

}
