package com.projetae.miagiques.metier;

import com.projetae.miagiques.dao.BilletRepository;
import com.projetae.miagiques.dao.EpreuveRepository;
import com.projetae.miagiques.entities.Billet;
import com.projetae.miagiques.entities.Epreuve;
import com.projetae.miagiques.entities.Spectateur;
import com.projetae.miagiques.utilities.BilletExceptions.BilletInexistant;
import com.projetae.miagiques.utilities.StatutBillet;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Collection;

@Service
public class BilletService {

    private final BilletRepository billetRepository;

    public BilletService(BilletRepository billetRepository) {
        this.billetRepository = billetRepository;
    }

    public void supprimerBillets(Collection<Billet> listeBillets) {
        for (Billet b : listeBillets) {
            this.billetRepository.deleteById(b.getIdBillet());
        }
    }

    public ResponseEntity<Boolean> validerBillet(Long idBillet, Long idEpreuve) throws BilletInexistant {
        StatutBillet etat;
        boolean isBilletValide;
        Long idEpreuveBillet;

        etat = this.getEtatBillet(idBillet);
        isBilletValide = StatutBillet.VALIDE == etat;

        if (!isBilletValide) {
            return ResponseEntity.ok(isBilletValide);
        }

        try {
            idEpreuveBillet = this.getIdEpreuveBillet(idBillet);
        } catch (BilletInexistant e) {
            throw new BilletInexistant(HttpStatus.BAD_GATEWAY);
        }

        isBilletValide = idEpreuve.equals(idEpreuveBillet);

        return ResponseEntity.ok(isBilletValide);
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

    /**
     * Compteur de billets valide pour une épreuve donnée
     *
     * @param client le spectateur
     * @param epreuve l'épreuve selectionnée par le client
     * @return le combre de billet valide que le client a déjà pour cette épreuve
     */
    public int compteBilletPourEpreuve(Spectateur client, Epreuve epreuve) {

        int compte;

        if (client == null || epreuve == null)
            throw new IllegalArgumentException("parameters may be null");

        compte = 0;

        for (Billet b : client.getBillets())
            if (epreuve == b.getEpreuve() && StatutBillet.VALIDE == b.getEtat())
                compte++;

        return compte;
    }

    public Billet getBilletAnnule(Epreuve epreuve) {
        return this.billetRepository.findByEpreuveAndEtatIs(epreuve, StatutBillet.ANNULE).stream().findAny().get();
    }

    public Billet getBillet(Long idBillet) {
        return this.billetRepository.findByIdBillet(idBillet);
    }

/*------------------*/


    public void saveBillet(Billet bi) {
        this.billetRepository.save(bi);
    }

}
