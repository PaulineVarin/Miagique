package com.projetae.miagiques.metier;

import com.projetae.miagiques.dao.BilletRepository;
import com.projetae.miagiques.dao.EpreuveRepository;
import com.projetae.miagiques.dao.SpectateurRepository;
import com.projetae.miagiques.dto.BilletDTO;
import com.projetae.miagiques.dto.EpreuveDTO;
import com.projetae.miagiques.dto.SelectionBilletDTO;
import com.projetae.miagiques.entities.Billet;
import com.projetae.miagiques.entities.Epreuve;
import com.projetae.miagiques.entities.Spectateur;
import com.projetae.miagiques.utilities.BilletExceptions.BilletAchatImpossible;
import com.projetae.miagiques.utilities.BilletExceptions.BilletInexistant;
import com.projetae.miagiques.utilities.BilletExceptions.TooManyBilletsException;
import com.projetae.miagiques.utilities.EpreuveExceptions.EpreuveInexistante;
import com.projetae.miagiques.utilities.PersonneExceptions.CompteInexistant;
import com.projetae.miagiques.utilities.StatutBillet;
import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.rsocket.RSocketProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;

import static java.util.Objects.isNull;

@Service
public class BilletService {

    private final BilletRepository billetRepository;
    private final EpreuveRepository epreuveRepository;

    private final SpectateurRepository spectateurRepository ;

    public BilletService(BilletRepository billetRepository, EpreuveRepository epreuveRepository, SpectateurRepository spectateurRepository) {
        this.billetRepository = billetRepository;
        this.epreuveRepository = epreuveRepository;
        this.spectateurRepository = spectateurRepository;
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
        isBilletValide = StatutBillet.GENERE == etat;

        if (!isBilletValide) {
            return ResponseEntity.ok(isBilletValide);
        }

        try {
            idEpreuveBillet = this.getIdEpreuveBillet(idBillet);
        } catch (BilletInexistant e) {
            throw new BilletInexistant(HttpStatus.BAD_GATEWAY);
        }

        isBilletValide = idEpreuve.equals(idEpreuveBillet);

        if(isBilletValide) {
            Billet b = this.billetRepository.findByIdBillet(idBillet) ;
            b.setEtat(StatutBillet.VALIDE);
            this.billetRepository.save(b);
        }

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
     * @param client  le spectateur
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

    public SelectionBilletDTO selectionnerUnBillet(String mail, Long idEpreuve) throws EpreuveInexistante, TooManyBilletsException, CompteInexistant {

        Billet bi;
        Epreuve ep;

        if (mail == null || idEpreuve == null)
            throw new IllegalArgumentException("At least, one parameter may be null");

        ep = this.epreuveRepository.findById(idEpreuve).get();

        if (ep == null)
            throw new EpreuveInexistante(HttpStatus.NOT_FOUND);

        if(isNull(this.spectateurRepository.findByEmailIs(mail))) {
            throw new CompteInexistant() ;
        };

        Spectateur spectateur = this.spectateurRepository.findByEmailIs(mail) ;


        if (4 <= this.compteBilletPourEpreuve(spectateur, ep)) {
            throw new TooManyBilletsException(HttpStatus.CONFLICT);
        }

        bi = this.getBilletAnnule(ep);

        if (bi != null) { // un billet annulé a été récupéré
            // on le récupère pour l'envoyer au client
            bi.setEtat(StatutBillet.GENERE);
        } else if (ep.getListeBillets().size() >= ep.getNbPlacesSpectateur()) {
            //l'épreuve a atteint le nombre maximal de billets réservables
            throw new TooManyBilletsException(HttpStatus.CONFLICT);
        } else { // encore des billets dispos pour l'épreuve mais aucun billet annulé à mettre à disposition
            bi = new Billet(50f, StatutBillet.GENERE, null, ep);
            ep.getListeBillets().add(bi);
        }

        this.billetRepository.save(bi); //on save l'état du billet dans la base
        this.epreuveRepository.save(ep); //on save l'état de l'épreuve
        return new SelectionBilletDTO(bi.getPrix(), bi.getIdBillet());
    }
    public BilletDTO achatBillet(@RequestBody Long idBillet, float prixAchat, String email)
            throws BilletInexistant, BilletAchatImpossible {

        Billet bi;
        ModelMapper modelMapper;
        modelMapper = new ModelMapper();
        BilletDTO biDTO = new BilletDTO() ;

        if (idBillet == null || prixAchat < 0)
            throw new IllegalArgumentException("billet may be null or prixAchat may be below 0");

        if ( (bi = this.billetRepository.findByIdBillet(idBillet)) == null)
            throw new BilletInexistant(HttpStatus.NOT_FOUND);

        if (StatutBillet.GENERE != bi.getEtat())
            throw new BilletAchatImpossible(HttpStatus.BAD_REQUEST);

        if (prixAchat < bi.getPrix()) {
            bi.setEtat(StatutBillet.ANNULE);
            this.billetRepository.save(bi);
            modelMapper.map(bi, biDTO);
            biDTO.setEpreuveId(bi.getEpreuve().getIdEpreuve());
            biDTO.setSpectateurId(bi.getSpectateur().getId());
            return biDTO; //on accepte que le client n'ait pas payé
        }

        bi.setEtat(StatutBillet.GENERE);

        Spectateur s = this.spectateurRepository.findByEmailIs(email);
        s.getBillets().add(bi) ;
        bi.setSpectateur(s);

        this.billetRepository.save(bi);
        modelMapper.map(bi, biDTO);
        biDTO.setEpreuveId(bi.getEpreuve().getIdEpreuve());
        biDTO.setSpectateurId(bi.getSpectateur().getId());
        return biDTO ;
    }

    public Billet getBilletAnnule(Epreuve epreuve) {
        if(this.billetRepository.findByEpreuveAndEtatIs(epreuve, StatutBillet.ANNULE).isEmpty()) {
            return null ;
        } else  {
            return this.billetRepository.findByEpreuveAndEtatIs(epreuve, StatutBillet.ANNULE).stream().findAny().get();
        }
    }
}
