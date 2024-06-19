package com.projetae.miagiques.metier;

import com.projetae.miagiques.dao.BilletRepository;
import com.projetae.miagiques.dao.EpreuveRepository;
import com.projetae.miagiques.dto.ObjectMapperUtils;
import com.projetae.miagiques.dto.StatistiqueEpreuveDTO;
import com.projetae.miagiques.entities.Epreuve;
import com.projetae.miagiques.entities.StatistiqueEpreuve;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class StatistiqueEpreuveService {
    private final EpreuveRepository epreuveRepository ;

    private final BilletRepository billetRepository ;


    public StatistiqueEpreuveService(EpreuveRepository epreuveRepository, BilletRepository billetRepository) {
        this.epreuveRepository = epreuveRepository;

        this.billetRepository = billetRepository;
    }
    private int calculerPlacesRestantes(Epreuve e) {
        return e.getNbPlacesSpectateur() - this.billetRepository.findAllByEpreuveIs(e).size() ;
    }
    public Collection<StatistiqueEpreuveDTO> getStatistiquesVentes() {
        Collection<Epreuve> listeEpreuves = new ArrayList<>() ;
        this.epreuveRepository.findAll().forEach(listeEpreuves::add);
        Collection<StatistiqueEpreuve> listeStatistiquesVente = new ArrayList<>() ;

        for(Epreuve e:listeEpreuves) {
            int nbBilletsRestants = calculerPlacesRestantes(e) ;
            int nbBillets = e.getNbPlacesSpectateur() ;

            StatistiqueEpreuve se = new StatistiqueEpreuve(e,nbBilletsRestants,nbBillets) ;
            listeStatistiquesVente.add(se) ;

        }
        return ObjectMapperUtils.mapAllStatistiques(listeStatistiquesVente, StatistiqueEpreuveDTO.class);
    }
}
