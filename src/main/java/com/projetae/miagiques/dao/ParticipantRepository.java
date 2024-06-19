package com.projetae.miagiques.dao;

import com.projetae.miagiques.entities.Participant;
import org.springframework.data.repository.CrudRepository;

public interface ParticipantRepository extends CrudRepository<Participant, Long> {
    public Participant findByEmail(String email);
}
