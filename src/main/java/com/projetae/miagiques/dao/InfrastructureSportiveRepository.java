package com.projetae.miagiques.dao;

import com.projetae.miagiques.entities.Epreuve;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.projetae.miagiques.entities.InfrastructureSportive;

import java.util.Collection;

public interface InfrastructureSportiveRepository extends CrudRepository<InfrastructureSportive, Long> {
    InfrastructureSportive findByNomIs(String nom) ;
    Collection<InfrastructureSportive> findByNomIsAndIdInfrastructureIsNot(String nom, Long idInfrastructure) ;
}
