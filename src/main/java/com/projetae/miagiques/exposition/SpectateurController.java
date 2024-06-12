package com.projetae.miagiques.exposition;

import com.projetae.miagiques.entities.Billet;
import com.projetae.miagiques.metier.SpectateurService;
import com.projetae.miagiques.utilities.BilletExceptions.BilletAnnulationImpossible;
import com.projetae.miagiques.utilities.BilletExceptions.BilletInexistant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/spectateurs/")
public class SpectateurController {
    @Autowired
    private SpectateurService spectateurService ;

    //necessaire de verifier avant chaque appel de methode si l'utilisateur est connecté
    //on utilise l'adresse email dans l'uri pour faire ça


    /**
     *
     * @param idSpectateur représente l'id d'un spectateur
     * @return la liste des billets de l'utilisateur
     */
    @GetMapping({"{id_spectateur}/listerBillets"})
    public Collection<Billet> getAllBillets(@PathVariable("id_spectateur") Long idSpectateur) {
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
    @PutMapping("{id_spectateur}/annulerBillet/{id_billet}")
    public String annulerBillet(@PathVariable("id_billet") Long idBillet, @PathVariable("id_spectateur") Long idSpectateur) throws BilletInexistant, BilletAnnulationImpossible {
        return this.spectateurService.annulerBillet(idBillet, idSpectateur);
    }
}
