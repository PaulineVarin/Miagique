package com.projetae.miagiques.dao;

import com.projetae.miagiques.entities.Billet;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface BilletRepository extends CrudRepository<Billet, Long> {
    Billet findByIdBilletIsAndSpectateurId(Long idB, Long idS) ;

    Collection<Billet> findAllBySpectateurId(Long idSpectateur) ;

    Billet findByIdBillet(Long idB);
}
