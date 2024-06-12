package com.projetae.miagiques.dao;

import com.projetae.miagiques.entities.Epreuve;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface EpreuveRepository extends CrudRepository<Epreuve, Long> {
    Epreuve findByNomIs(String nom) ;

}
