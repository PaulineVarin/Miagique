package com.projetae.miagiques.metier;

import com.projetae.miagiques.dao.PersonneRepository;
import com.projetae.miagiques.entities.Personne;
import com.projetae.miagiques.utilities.PersonneExceptions.CompteInexistant;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PersonneService {
    private final PersonneRepository personneRepository ;

    public PersonneService(PersonneRepository personneRepository) {
        this.personneRepository = personneRepository;
    }

    public Collection<Personne> seConnecter(String mail) throws CompteInexistant {
        if(this.personneRepository.findByEmailIs(mail).isEmpty()) {
            throw new CompteInexistant() ;
        }
        return this.personneRepository.findByEmailIs(mail) ;
    }

}
