package com.projetae.miagiques.exposition;

import com.projetae.miagiques.entities.Controleur;
import com.projetae.miagiques.entities.Personne;
import com.projetae.miagiques.metier.BilletService;
import com.projetae.miagiques.metier.PersonneService;
import com.projetae.miagiques.utilities.BilletExceptions.BilletInexistant;
import com.projetae.miagiques.utilities.PersonneExceptions.CompteInexistant;
import com.projetae.miagiques.utilities.PersonneExceptions.RoleIncorrect;
import com.projetae.miagiques.utilities.StatutBillet;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/billet/{emailUtilisateur}")
public class BilletController {

    @Autowired
    private BilletService billetService;
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
     * Renvoie si le billet est valide pour l'épreuve ou non
     *
     * @param idBillet  l'identifiant unique d'un billet
     * @param idEpreuve l'identifiant unique d'une épreuve
     * @return false si le billet n'est pas valide ou si il n'est pas lié à l'épreuve
     * true sinon
     * @throws BilletInexistant l'identifiant précisé n'existe pas dans la base de données
     */
    @GetMapping({"/{idBillet}/{idEpreuve}/validite"})
    public ResponseEntity<Boolean> valideUnBillet(@PathVariable("emailUtilisateur") String email, @PathVariable("idBillet") Long idBillet, @PathVariable("idEpreuve") Long idEpreuve)
            throws BilletInexistant, RoleIncorrect, CompteInexistant {

        this.testerRole(email, Controleur.class) ;

        StatutBillet etat;
        boolean isBilletValide;
        Long idEpreuveBillet;

        etat = this.billetService.getEtatBillet(idBillet);
        isBilletValide = StatutBillet.VALIDE == etat;

        if (!isBilletValide) {
            return ResponseEntity.ok(isBilletValide);
        }

        try {
            idEpreuveBillet = this.billetService.getIdEpreuveBillet(idBillet);
        } catch (BilletInexistant e) {
            throw new BilletInexistant(HttpStatus.BAD_GATEWAY);
        }

        isBilletValide = idEpreuve == idEpreuveBillet;

        return ResponseEntity.ok(isBilletValide);
    }
}
