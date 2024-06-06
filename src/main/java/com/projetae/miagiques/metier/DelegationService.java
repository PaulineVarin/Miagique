package com.projetae.miagiques.metier;

import com.projetae.miagiques.dao.DelegationRepository;
import com.projetae.miagiques.entities.Delegation;
import com.projetae.miagiques.utilities.DelegationExistante;
import com.projetae.miagiques.utilities.DelegationInexistante;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DelegationService {
    private final DelegationRepository delegationRepository;

    public DelegationService(DelegationRepository delegationRepository) {
        this.delegationRepository = delegationRepository;
    }

    public Delegation creerDelegation(String nom) throws DelegationExistante {
        Delegation delegation = this.delegationRepository.findByNom(nom);
        if(delegation == null) {
            delegation = new Delegation(nom);
            this.delegationRepository.save(delegation);
        } else {
            throw new DelegationExistante();
        }
        return delegation;
    }

    public void supprimerDelegation(Long idD) throws DelegationInexistante {
        if(this.delegationRepository.findById(idD).isPresent()) {
            Delegation delegation = this.delegationRepository.findById(idD).get();
            this.delegationRepository.deleteById(idD);
        } else {
            throw new DelegationInexistante();
        }

    }

}
