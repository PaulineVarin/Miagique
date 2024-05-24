package com.projetae.miagiques.dao;

import com.projetae.miagiques.entities.Personne;
import org.springframework.data.repository.CrudRepository;

public interface PersonneRepository extends CrudRepository<Personne, Long> {
}
