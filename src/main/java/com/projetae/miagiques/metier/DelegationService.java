package com.projetae.miagiques.metier;

import com.projetae.miagiques.dao.DelegationRepository;
import com.projetae.miagiques.dto.DelegationDTO;
import com.projetae.miagiques.dto.EpreuveDTO;
import com.projetae.miagiques.dto.ObjectMapperUtils;
import com.projetae.miagiques.entities.Delegation;
import com.projetae.miagiques.entities.Epreuve;
import com.projetae.miagiques.utilities.DelegationExceptions.DelegationAvecParticipant;
import com.projetae.miagiques.utilities.DelegationExceptions.DelegationExistante;
import com.projetae.miagiques.utilities.DelegationExceptions.DelegationInexistante;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

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

    public ResponseEntity<String> supprimerDelegation(Long idD) throws DelegationInexistante, DelegationAvecParticipant {

        Delegation delegation;

        if(this.delegationRepository.findById(idD).isEmpty()) {
            throw new DelegationInexistante();
        }

        delegation = this.delegationRepository.findById(idD).get();
        if (delegation.getParticipants().size() > 0)
            throw new DelegationAvecParticipant(HttpStatus.CONFLICT);

        this.delegationRepository.deleteById(idD);

        return new ResponseEntity<>("Suppression OK", HttpStatus.OK);
    }

    public Collection<DelegationDTO> consulterDelegations() {
        Collection<Delegation> delegationliste = new ArrayList<>() ;
        this.delegationRepository.findAll().forEach(delegationliste::add);
        return ObjectMapperUtils.mapAllDelegations(delegationliste, DelegationDTO.class);
    }
}
