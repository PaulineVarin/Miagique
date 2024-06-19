package com.projetae.miagiques.exposition;

import com.projetae.miagiques.entities.*;
import com.projetae.miagiques.metier.BilletService;
import com.projetae.miagiques.metier.EpreuveService;
import com.projetae.miagiques.metier.PersonneService;
import com.projetae.miagiques.metier.SpectateurService;
import com.projetae.miagiques.utilities.BilletExceptions.BilletAnnulationImpossible;
import com.projetae.miagiques.utilities.BilletExceptions.BilletInexistant;
import com.projetae.miagiques.utilities.EpreuveExceptions.EpreuveInexistante;
import com.projetae.miagiques.utilities.PersonneExceptions.CompteInexistant;
import com.projetae.miagiques.utilities.PersonneExceptions.RoleIncorrect;
import com.projetae.miagiques.utilities.StatutBillet;
import com.projetae.miagiques.utilities.BilletExceptions.TooManyBilletsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private EpreuveService epreuveService;
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
    public Collection<Billet> getAllBillets(@PathVariable("emailUtilisateur") String email, @PathVariable("idSpectateur") Long idSpectateur) throws RoleIncorrect, CompteInexistant {
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

        isBilletValide = idEpreuve.equals(idEpreuveBillet);

        return ResponseEntity.ok(isBilletValide);
    }

    /**
     * Accessible par le spectateur uniquement
     * Affiche les offres pour les billets disponibles pour l'épreuve
     *
     * @param personne doit impérativement être de type Spectateur
     *                 limitée à 4 billets maximum
     * @param idEpreuve L'épreuve souhaitée par le spectateur
     * @return
     */
    @PostMapping("/selection")
    public ResponseEntity<selectionBilletDTO> selectionnerUnBillet(@RequestBody Personne personne, @RequestBody Long idEpreuve)
            throws  TooManyBilletsException, EpreuveInexistante {

        Spectateur client;
        Billet bi;
        Epreuve ep;

        if (personne == null || idEpreuve == null)
            throw new IllegalArgumentException("At least, one parameter may be null");


        client = (Spectateur) personne;
        ep = this.epreuveService.getEpreuveByIdEpreuve(idEpreuve).get();

        if (ep == null)
            throw new EpreuveInexistante(HttpStatus.NOT_FOUND);

        if (4 <= compteBilletPourEpreuve(client, ep)) {
            throw new TooManyBilletsException(HttpStatus.CONFLICT);
        }

        bi = getUnBilletAnnule(ep);

        if (bi != null) { // un billet annulé a été récupéré
            // on le remet à l'état valide et on l'attribu au spectateur
            bi.setSpectateur(client);
        } else if (ep.getListeBillets().size() >= ep.getNbPlacesSpectateur()) {
            //l'épreuve a atteint le nombre maximal de billets réservable
            throw new TooManyBilletsException(HttpStatus.CONFLICT);
        } else {
            bi = new Billet(50f, StatutBillet.ANNULE, client, ep);
        }

        this.billetService.saveBillet(bi); //on save l'état du billet dans la base
        return ResponseEntity.ok(new selectionBilletDTO(bi.getPrix(), bi));
    }

    /**
     *
     * @param idBillet
     * @param idEpreuve
     * @param prixAchat
     * @return null si l'achat n'est pas réalisé
     *          sinon return le billet valide et lié au clien
     */
    @PostMapping("/achat")
    public ResponseEntity<Billet> achatBillet(@RequestBody Long idBillet, @RequestBody Long idEpreuve, float prixAchat) {

        Billet bi;
        Epreuve ep;

        if (idBillet == null || idEpreuve == null)
            throw new IllegalArgumentException("billet or epreuve maybe null");

        if ( (bi = this.billetService.getBillet(idBillet)) == null
            || (ep = this.epreuveService.getEpreuveByIdEpreuve(idEpreuve).get()) == null
            || bi.getEpreuve() != ep
           )
            throw new IllegalArgumentException("billet or epreuve may be not exists on the data base or may be this billet is not linked to epreuve");

        if (prixAchat <= 0f)
            return ResponseEntity.ok(null); //on accepte que le client n'ait pas payé

        bi.setEtat(StatutBillet.VALIDE);
        this.billetService.saveBillet(bi);
        return ResponseEntity.ok(bi);
    }

    private class selectionBilletDTO {
        private float prixBillet;
        private Billet billet;

        //TODO utiliser le Mapper de Pauline (tout du moins l'exemple du mapper de pauline dans EpreuveService)
        public selectionBilletDTO(float prixBillet, Billet billet){
            this.prixBillet = prixBillet;
            this.billet = billet;
        }
    }

    /**
     * Compteur de billets valide pour une épreuve donnée
     *
     * @param client le spectateur
     * @param epreuve l'épreuve selectionnée par le client
     * @return le combre de billet valide que le client a déjà pour cette épreuve
     */
    private int compteBilletPourEpreuve(Spectateur client, Epreuve epreuve) {

        int compte;

        if (client == null || epreuve == null)
            throw new IllegalArgumentException("parameters may be null");

        compte = 0;

        for (Billet b : client.getBillets())
            if (epreuve == b.getEpreuve() && StatutBillet.VALIDE == b.getEtat())
                compte++;

        return compte;
    }

    /**
     * Selectionne un billet annulé dans une épreuve
     *
     * @param epreuve sélectionnée
     * @return null si aucun billet annulé pour cette épreuve
     *         sinon retourne le premier billet annulé venu
     */
    private Billet getUnBilletAnnule(Epreuve epreuve) {

        if (epreuve == null)
            throw new IllegalArgumentException("One parameter may be null");
        /*
        // si il n'existe pas de billet annulaaaaay
        Stream streamBilletsEpreuve = ((Stream)epreuve.getListeBillets()).filter(b -> StatutBillet.ANNULE == ((Billet) b).getEtat());
        if (streamBilletsEpreuve.count() == 0)
            return null;

        return ((List<Billet>)streamBilletsEpreuve.toList()).get(0);
         */

        return this.billetService.getBilletAnnule(epreuve);
    }
}
