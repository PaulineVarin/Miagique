package com.projetae.miagiques.dao;

import com.projetae.miagiques.entities.Epreuve;
import org.springframework.data.repository.CrudRepository;
import java.util.Collection;

public interface EpreuveRepository extends CrudRepository<Epreuve, Long> {
    Epreuve findByNomIs(String nom) ;

    Collection<Epreuve> findByNomIsAndIdEpreuveIsNot(String nom, Long idEpreuve) ;

}
