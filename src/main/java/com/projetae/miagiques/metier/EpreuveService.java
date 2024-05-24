package com.projetae.miagiques.metier;

import com.projetae.miagiques.dao.EpreuveRepository;
import com.projetae.miagiques.entities.Epreuve;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class EpreuveService {

    private final EpreuveRepository epreuveRepository;

    public EpreuveService(EpreuveRepository epreuveRepository) {
        this.epreuveRepository = epreuveRepository;
    }

    public Collection<Epreuve> getAll(){

        return (Collection<Epreuve>) this.epreuveRepository.findAll();
    }
}
