package com.projetae.miagiques.dao;

import com.projetae.miagiques.entities.Billet;
import com.projetae.miagiques.entities.Epreuve;
import com.projetae.miagiques.utilities.StatutBillet;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface BilletRepository extends CrudRepository<Billet, Long> {
    Billet findByIdBilletIsAndSpectateurId(Long idB, Long idS) ;

    Collection<Billet> findAllBySpectateurId(Long idSpectateur) ;

    Billet findByIdBillet(Long idB);

    Collection<Billet> findByEpreuveAndEtatIs(Epreuve epreuve, StatutBillet etat);
}
