package com.projetae.miagiques.metier;

import com.projetae.miagiques.dao.BilletRepository;
import com.projetae.miagiques.entities.Billet;
import com.projetae.miagiques.entities.Epreuve;
import com.projetae.miagiques.utilities.BilletExceptions.BilletAnnulationImpossible;
import com.projetae.miagiques.utilities.BilletExceptions.BilletInexistant;
import com.projetae.miagiques.utilities.StatutBillet;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.Collection;

import static java.util.Objects.isNull;

@Service
public class SpectateurService {
    private final BilletRepository billetRepository ;


    public SpectateurService(BilletRepository billetRepository) {
        this.billetRepository = billetRepository;
    }

    public Collection<Billet> getAllBillets(Long idSpectateur) {
        return this.billetRepository.findAllBySpectateurId(idSpectateur) ;
    }

    public String annulerBillet(Long idBillet, Long idSpectateur) throws BilletInexistant, BilletAnnulationImpossible {
        Billet bi = this.billetRepository.findByIdBilletIsAndSpectateurId(idBillet, idSpectateur);
        if (isNull(bi)) {
            throw new BilletInexistant();
        }

        Epreuve e = bi.getEpreuve();
        Timestamp dateEpreuve = e.getDate();
        Timestamp dateCourante = new Timestamp(System.currentTimeMillis());
        Long differenceJours = Duration.between(dateCourante.toLocalDateTime(), dateEpreuve.toLocalDateTime()).toDays();

        if (differenceJours < 3) {
            throw new BilletAnnulationImpossible();
        }

        bi.setEtat(StatutBillet.ANNULE);
        this.billetRepository.save(bi);
        float montantRembourse = 0;

        if (differenceJours > 7) {
            montantRembourse = bi.getPrix();
        } else {
            montantRembourse = bi.getPrix() * 0.5f;
        }

        String m = "Opération de remboursement réussi : " + montantRembourse;
        return m;
    }
}
