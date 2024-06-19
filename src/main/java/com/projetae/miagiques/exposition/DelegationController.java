package com.projetae.miagiques.exposition;

import com.projetae.miagiques.entities.Delegation;
import com.projetae.miagiques.metier.DelegationService;
import com.projetae.miagiques.utilities.DelegationExceptions.DelegationExistante;
import com.projetae.miagiques.utilities.DelegationExceptions.DelegationInexistante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/delegation")
public class DelegationController {

    @Autowired
    private DelegationService delegationService;

    @PostMapping("/creerDelegation")
    public Delegation creerDelegation( @RequestBody String nom) throws DelegationExistante {
        return delegationService.creerDelegation(nom);
    }

    @DeleteMapping("/supprimerDelegation")
    public void supprimerDelegation(@RequestBody Long idD) throws DelegationInexistante {
       delegationService.supprimerDelegation(idD);
    }

}
