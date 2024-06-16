package com.projetae.miagiques.metier;

import com.projetae.miagiques.dao.DelegationRepository;
import com.projetae.miagiques.entities.Delegation;
import com.projetae.miagiques.utilities.DelegationExceptions.DelegationExistante;
import com.projetae.miagiques.utilities.DelegationExceptions.DelegationInexistante;
import org.springframework.stereotype.Service;

@Service
public class DelegationService {
    private final DelegationRepository delegationRepository;

    public DelegationService(DelegationRepository delegationRepository) {
        this.delegationRepository = delegationRepository;
    }

    public Delegation creerDelegation(String nom) throws DelegationExistante {
        Delegation delegation = this.delegationRepository.findByNom(nom);
        if(delegation != null) {
            throw new DelegationExistante();

        }
        delegation = new Delegation(nom);
        this.delegationRepository.save(delegation);
        return delegation;
    }

    public void supprimerDelegation(Long idD) throws DelegationInexistante {
        if(!this.delegationRepository.findById(idD).isPresent()) {
            throw new DelegationInexistante();

        }
        Delegation delegation = this.delegationRepository.findById(idD).get();
        this.delegationRepository.deleteById(idD);
    }

}
