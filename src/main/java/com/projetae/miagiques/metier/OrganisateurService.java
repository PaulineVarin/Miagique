package com.projetae.miagiques.metier;

import com.projetae.miagiques.dao.OrganisateurRepository;
import com.projetae.miagiques.dao.PersonneRepository;
import com.projetae.miagiques.dto.EpreuveDTO;
import com.projetae.miagiques.dto.OrganisateurDTO;
import com.projetae.miagiques.entities.Organisateur;
import com.projetae.miagiques.entities.Participant;
import com.projetae.miagiques.entities.Personne;
import com.projetae.miagiques.utilities.PersonneExceptions.CompteExiste;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
public class OrganisateurService {

    private final OrganisateurRepository organisateurRepository ;

    public OrganisateurService(OrganisateurRepository organisateurRepository) {
        this.organisateurRepository = organisateurRepository;

    }

    public OrganisateurDTO creationOrganisateur(OrganisateurDTO infosOrganisateur ) throws CompteExiste {
        if(isNull(this.organisateurRepository.findByEmailIs(infosOrganisateur.getEmail()))) {

            Organisateur organisateur = new Organisateur(infosOrganisateur.getEmail(),infosOrganisateur.getNom(),infosOrganisateur.getPrenom()) ;
            this.organisateurRepository.save(organisateur) ;
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(organisateur, OrganisateurDTO.class);

        } else {
            throw new CompteExiste() ;
        }


    }

}
