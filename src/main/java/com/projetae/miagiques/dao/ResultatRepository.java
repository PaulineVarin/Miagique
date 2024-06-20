package com.projetae.miagiques.dao;

import com.projetae.miagiques.entities.Epreuve;
import com.projetae.miagiques.entities.Participant;
import com.projetae.miagiques.entities.Resultat;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface ResultatRepository extends CrudRepository<Resultat, Long> {

    Resultat findByEpreuveAndParticipantAndPosition(Epreuve epreuve, Participant participant, int position);

    Collection<Resultat> findByParticipant(Participant participant);
}
