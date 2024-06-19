package com.projetae.miagiques.metier;

import com.projetae.miagiques.dao.EpreuveRepository;
import com.projetae.miagiques.entities.Billet;
import com.projetae.miagiques.entities.Epreuve;
import com.projetae.miagiques.utilities.EpreuveInexistanteException;
import com.projetae.miagiques.utilities.StatutBillet;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class EpreuveService {

    private final EpreuveRepository epreuveRepository;

    public EpreuveService(EpreuveRepository epreuveRepository) {
        this.epreuveRepository = epreuveRepository;
    }

    public Collection<Epreuve> getAll(){

        return (Collection<Epreuve>) this.epreuveRepository.findAll();
    }

    public Optional<Epreuve> getEpreuveByIdEpreuve(Long idEpreuve) {
        return this.epreuveRepository.findById(idEpreuve);
    }
}
