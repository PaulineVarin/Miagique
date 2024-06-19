package com.projetae.miagiques.metier;

import com.projetae.miagiques.dao.BilletRepository;
import com.projetae.miagiques.dao.ResultatRepository;
import com.projetae.miagiques.entities.Resultat;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ResultatService {
    private final ResultatRepository resultatRepository ;

    public ResultatService(ResultatRepository resultatRepository) {
        this.resultatRepository = resultatRepository;
    }

    public void supprimerResultats(Collection<Resultat> listeResultats) {
        for (Resultat r : listeResultats) {
            this.resultatRepository.deleteById(r.getIdResultat());
        }
    }
}
