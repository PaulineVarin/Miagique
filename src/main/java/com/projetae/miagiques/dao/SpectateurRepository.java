package com.projetae.miagiques.dao;

import com.projetae.miagiques.entities.Organisateur;
import com.projetae.miagiques.entities.Spectateur;
import org.springframework.data.repository.CrudRepository;

public interface SpectateurRepository extends CrudRepository<Spectateur, Long> {
    public Spectateur findByEmailIs(String mail) ;
}
