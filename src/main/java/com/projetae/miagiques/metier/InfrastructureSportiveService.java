package com.projetae.miagiques.metier;

import com.projetae.miagiques.dao.InfrastructureSportiveRepository;
import com.projetae.miagiques.dto.InfrastructureSportiveDTO;
import com.projetae.miagiques.entities.InfrastructureSportive;
import com.projetae.miagiques.utilities.InfrastructureSportiveExceptions.InfrastructureSportiveExiste;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
}
