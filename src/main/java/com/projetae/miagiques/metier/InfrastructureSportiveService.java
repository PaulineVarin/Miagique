package com.projetae.miagiques.metier;

import com.projetae.miagiques.dao.InfrastructureSportiveRepository;
import com.projetae.miagiques.dto.InfrastructureSportiveDTO;
import com.projetae.miagiques.dto.ObjectMapperUtils;
import com.projetae.miagiques.entities.InfrastructureSportive;
import com.projetae.miagiques.utilities.InfrastructureSportiveExceptions.InfrastructureSportiveExiste;
import com.projetae.miagiques.utilities.InfrastructureSportiveExceptions.InfrastructureSportiveInexistante;
import com.projetae.miagiques.utilities.InfrastructureSportiveExceptions.InfrastructureSportiveReference;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;
import static java.util.Objects.isNull;

@Service
public class InfrastructureSportiveService {
    private final InfrastructureSportiveRepository infrastructureSportiveRepository ;

    public InfrastructureSportiveService(InfrastructureSportiveRepository infrastructureSportiveRepository) {
        this.infrastructureSportiveRepository = infrastructureSportiveRepository;
    }

    public InfrastructureSportiveDTO creationInfrastructure(InfrastructureSportiveDTO i) throws InfrastructureSportiveExiste {
        if(isNull(this.infrastructureSportiveRepository.findByNomIs(i.getNom()))) {

            InfrastructureSportive infrastructure = new InfrastructureSportive(i.getNom(),i.getCapacite(),i.getAdresse()) ;
            this.infrastructureSportiveRepository.save(infrastructure);
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(infrastructure, InfrastructureSportiveDTO.class);
        }
        else {
            throw new InfrastructureSportiveExiste() ;
        }
    }

    public InfrastructureSportiveDTO miseAJourInfrastructure(InfrastructureSportiveDTO infrastructureUpdate, Long idI) throws InfrastructureSportiveInexistante, InfrastructureSportiveExiste {
        if(this.infrastructureSportiveRepository.findById(idI).isEmpty()) {
            throw new InfrastructureSportiveInexistante() ;
        }

        InfrastructureSportive i = this.infrastructureSportiveRepository.findById(idI).get() ;

        if(!this.infrastructureSportiveRepository.findByNomIsAndIdInfrastructureIsNot(infrastructureUpdate.getNom(), idI).isEmpty()) {
            throw new InfrastructureSportiveExiste() ;
        }

        i.updateInfrastructure(infrastructureUpdate) ;
        this.infrastructureSportiveRepository.save(i) ;
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(i, InfrastructureSportiveDTO.class);
    }

    public ResponseEntity<String> supprimerInfrastructure(Long idInfrastructure) throws InfrastructureSportiveInexistante, InfrastructureSportiveReference {
        if(this.infrastructureSportiveRepository.findById(idInfrastructure).isEmpty()) {
            throw new InfrastructureSportiveInexistante() ;
        }
        InfrastructureSportive i = this.infrastructureSportiveRepository.findById(idInfrastructure).get() ;

        if(!i.getListeEpreuves().isEmpty()) {
            throw new InfrastructureSportiveReference() ;
        }

        this.infrastructureSportiveRepository.deleteById(idInfrastructure);
        return new ResponseEntity<>("Suppression ok", HttpStatus.OK) ;
    }

    public Collection<InfrastructureSportiveDTO> getAllInfrastructures() {
        Collection<InfrastructureSportive> infrastructureliste = new ArrayList<>() ;
        this.infrastructureSportiveRepository.findAll().forEach(infrastructureliste::add);
        return ObjectMapperUtils.mapAllInfrastructures(infrastructureliste,InfrastructureSportiveDTO.class);
    }
}
