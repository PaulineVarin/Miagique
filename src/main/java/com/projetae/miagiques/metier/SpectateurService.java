package com.projetae.miagiques.metier;

import com.projetae.miagiques.dao.SpectateurRepository;
import com.projetae.miagiques.entities.Billet;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class SpectateurService {
    private final SpectateurRepository spectateurRepository ;

    public SpectateurService(SpectateurRepository spectateurRepository) {
        this.spectateurRepository = spectateurRepository;
    }

    public Collection<Billet> getBilletsSpectateur(Long idSpectateur) {
       return null ;
    }


}
