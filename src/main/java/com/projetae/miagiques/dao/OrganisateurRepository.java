package com.projetae.miagiques.dao;

import com.projetae.miagiques.entities.Organisateur;
import com.projetae.miagiques.entities.Personne;
import jakarta.persistence.Id;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface OrganisateurRepository extends CrudRepository<Organisateur, Long> {
    public Organisateur findByEmailIs(String mail) ;
}
