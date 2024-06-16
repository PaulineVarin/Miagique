package com.projetae.miagiques.dao;

import com.projetae.miagiques.entities.Personne;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface PersonneRepository extends CrudRepository<Personne, Long> {
    public Collection<Personne> findByEmailIs(String mail) ;
}
