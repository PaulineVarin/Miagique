package com.projetae.miagiques.exposition;

import com.projetae.miagiques.dto.BilletDTO;
import com.projetae.miagiques.dto.SelectionBilletDTO;
import com.projetae.miagiques.entities.*;
import com.projetae.miagiques.metier.BilletService;
import com.projetae.miagiques.metier.PersonneService;
import com.projetae.miagiques.metier.SpectateurService;
import com.projetae.miagiques.utilities.BilletExceptions.BilletAchatImpossible;
import com.projetae.miagiques.utilities.BilletExceptions.BilletAnnulationImpossible;
import com.projetae.miagiques.utilities.BilletExceptions.BilletInexistant;
import com.projetae.miagiques.utilities.EpreuveExceptions.EpreuveInexistante;
import com.projetae.miagiques.utilities.PersonneExceptions.CompteInexistant;
import com.projetae.miagiques.utilities.PersonneExceptions.RoleIncorrect;
import com.projetae.miagiques.utilities.BilletExceptions.TooManyBilletsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("/billet/{emailUtilisateur}")
public class BilletController {

    @Autowired
    private BilletService billetService;
    @Autowired
    private PersonneService personneService;
    @Autowired
    private SpectateurService spectateurService;


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
    @GetMapping({"/{idSpectateur}/listerBillets"})
    public Collection<BilletDTO> getAllBillets(@PathVariable("emailUtilisateur") String email, @PathVariable("idSpectateur") Long idSpectateur) throws RoleIncorrect, CompteInexistant {
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
    @PutMapping("/{idSpectateur}/annulerBillet/{id_billet}")
    public String annulerBillet(@PathVariable("emailUtilisateur") String email, @PathVariable("id_billet") Long idBillet, @PathVariable("idSpectateur") Long idSpectateur) throws BilletInexistant, BilletAnnulationImpossible, RoleIncorrect, CompteInexistant {
        this.testerRole(email, Spectateur.class) ;
        return this.spectateurService.annulerBillet(idBillet, idSpectateur);
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
    public ResponseEntity<Boolean> valideUnBillet(@PathVariable("emailUtilisateur") String email, @PathVariable("idBillet") Long idBillet, @PathVariable("idEpreuve") Long idEpreuve) throws RoleIncorrect, CompteInexistant, BilletInexistant {
        this.testerRole(email,Controleur.class);
        return this.billetService.validerBillet(idBillet, idEpreuve);
    }

    /**
     * Accessible par le spectateur uniquement
     * Affiche les offres pour les billets disponibles pour l'épreuve
     *
     *
     * @param idEpreuve L'épreuve souhaitée par le spectateur
     * @return
     */
    @PostMapping("/selection/{idEpreuve}")
    public SelectionBilletDTO selectionnerUnBillet(@PathVariable("emailUtilisateur") String email, @PathVariable("idEpreuve") Long idEpreuve)
            throws TooManyBilletsException, EpreuveInexistante, RoleIncorrect, CompteInexistant {

        this.testerRole(email,Spectateur.class);
        return this.billetService.selectionnerUnBillet(email, idEpreuve);
    }

    /**
     *
     * @param selectionBilletDTO
     * @return null si l'achat n'est pas réalisé
     *          sinon return le billet valide et lié au clien
     */
    @PostMapping("/achat")
    public BilletDTO achatBillet(@PathVariable("emailUtilisateur") String email, @RequestBody SelectionBilletDTO selectionBilletDTO)
            throws RoleIncorrect, CompteInexistant, BilletInexistant, BilletAchatImpossible {
        this.testerRole(email, Spectateur.class);
        return this.billetService.achatBillet(selectionBilletDTO.getIdBillet(), selectionBilletDTO.getPrixBillet(),email);
    }

}
