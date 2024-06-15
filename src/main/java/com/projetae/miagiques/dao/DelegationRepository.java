package com.projetae.miagiques.dao;

import com.projetae.miagiques.entities.Delegation;
import com.projetae.miagiques.entities.Participant;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface DelegationRepository extends CrudRepository<Delegation, Long> {

    public Delegation findByNom(String nom);

}
