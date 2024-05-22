package com.projetae.miagiques.dao;

import com.projetae.miagiques.entities.Organisateur;
import jakarta.persistence.Id;
import org.springframework.data.repository.CrudRepository;

public interface OrganisateurRepository extends CrudRepository<Organisateur, Long> {
}
