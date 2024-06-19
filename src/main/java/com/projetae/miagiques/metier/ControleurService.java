package com.projetae.miagiques.metier;

import com.projetae.miagiques.dao.ControleurRepository;
import com.projetae.miagiques.dto.ControleurDTO;
import com.projetae.miagiques.entities.Controleur;
import com.projetae.miagiques.utilities.PersonneExceptions.CompteExiste;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
public class ControleurService {
    private final ControleurRepository controleurRepository ;

    public ControleurService(ControleurRepository controleurRepository) {
        this.controleurRepository = controleurRepository;
    }

    public ControleurDTO creerControleur(ControleurDTO controleurInfos) throws CompteExiste {
        if(isNull(this.controleurRepository.findByEmailIs(controleurInfos.getEmail()))) {

            Controleur controleur = new Controleur(controleurInfos.getEmail(),controleurInfos.getNom(),controleurInfos.getPrenom()) ;
            this.controleurRepository.save(controleur) ;
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(controleur, ControleurDTO.class);

        } else {
            throw new CompteExiste() ;
        }
    }
}
