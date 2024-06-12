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

    @GetMapping({"{id_spectateur}/listerBillets"})
    public Collection<Billet> getAllBillets(@PathVariable("id_spectateur") Long idSpectateur) {
        return this.spectateurService.getAllBillets(idSpectateur) ;
    }
    @PutMapping("{id_spectateur}/annulerBillet/{id_billet}")
    public String annulerBillet(@PathVariable("id_billet") Long idBillet, @PathVariable("id_spectateur") Long idSpectateur) throws BilletInexistant, BilletAnnulationImpossible {
        return this.spectateurService.annulerBillet(idBillet, idSpectateur);
    }
}
