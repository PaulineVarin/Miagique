package com.projetae.miagiques.metier;

import com.projetae.miagiques.dao.BilletRepository;
import com.projetae.miagiques.dao.EpreuveRepository;
import com.projetae.miagiques.entities.Billet;
import com.projetae.miagiques.entities.Epreuve;
import com.projetae.miagiques.utilities.BilletExceptions.BilletInexistant;
import com.projetae.miagiques.utilities.StatutBillet;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.Collection;

@Service
public class BilletService {

    private final BilletRepository billetRepository;

    public BilletService(BilletRepository billetRepository) {
        this.billetRepository = billetRepository;
    }

    public Billet getBillet(Long idBillet) {
        return this.billetRepository.findByIdBillet(idBillet);
    }

    public StatutBillet getEtatBillet(Long idBillet) throws BilletInexistant {

        Billet bi = this.billetRepository.findByIdBillet(idBillet);

        if (bi == null)
            throw new BilletInexistant(HttpStatus.NOT_FOUND);

        return bi.getEtat();
    }

    public Long getIdEpreuveBillet(Long idBillet) throws BilletInexistant {

        Billet bi = this.billetRepository.findByIdBillet(idBillet);

        if (bi == null)
            throw new BilletInexistant(HttpStatus.NOT_FOUND);

        return bi.getEpreuve().getIdEpreuve();
    }

    public Billet getBilletAnnule(Epreuve epreuve) {
        return this.billetRepository.findByEpreuveAndEtatIs(epreuve, StatutBillet.ANNULE).stream().findAny().get();
    }

    public void saveBillet(Billet bi) {
        this.billetRepository.save(bi);
    }

    public void supprimerBillets(Collection<Billet> listeBillets) {
        for (Billet b : listeBillets) {
            this.billetRepository.deleteById(b.getIdBillet());
        }
    }
}
