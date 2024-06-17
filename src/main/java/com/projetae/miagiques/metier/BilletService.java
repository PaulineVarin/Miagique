package com.projetae.miagiques.metier;

import com.projetae.miagiques.dao.BilletRepository;
import com.projetae.miagiques.entities.Billet;
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

    public void supprimerBillets(Collection<Billet> listeBillets) {
        for (Billet b : listeBillets) {
            this.billetRepository.deleteById(b.getIdBillet());
        }
    }
}
