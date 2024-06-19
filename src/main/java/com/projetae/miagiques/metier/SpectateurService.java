package com.projetae.miagiques.metier;

import com.projetae.miagiques.dao.BilletRepository;
import com.projetae.miagiques.dao.SpectateurRepository;
import com.projetae.miagiques.dto.BilletDTO;
import com.projetae.miagiques.dto.EpreuveDTO;
import com.projetae.miagiques.dto.ObjectMapperUtils;
import com.projetae.miagiques.dto.SpectateurDTO;
import com.projetae.miagiques.entities.Billet;
import com.projetae.miagiques.entities.Epreuve;
import com.projetae.miagiques.entities.Spectateur;
import com.projetae.miagiques.utilities.BilletExceptions.BilletAnnulationImpossible;
import com.projetae.miagiques.utilities.BilletExceptions.BilletInexistant;
import com.projetae.miagiques.utilities.PersonneExceptions.CompteExiste;
import com.projetae.miagiques.utilities.PersonneExceptions.CompteInexistant;
import com.projetae.miagiques.utilities.BilletExceptions.ExisteBillets;
import com.projetae.miagiques.utilities.StatutBillet;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;

import static java.util.Objects.isNull;

@Service
public class SpectateurService {
    private final BilletRepository billetRepository ;

    private final SpectateurRepository spectateurRepository ;


    public SpectateurService(BilletRepository billetRepository, SpectateurRepository spectateurRepository) {
        this.billetRepository = billetRepository;
        this.spectateurRepository = spectateurRepository;
    }

    public Collection<BilletDTO> getAllBillets(Long idSpectateur) {
        Collection<Billet> billetliste = new ArrayList<>() ;
        this.billetRepository.findAllBySpectateurId(idSpectateur).forEach(billetliste::add);
        return ObjectMapperUtils.mapAllBillets(billetliste, BilletDTO.class);

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

    public SpectateurDTO creerSpectateur(SpectateurDTO spectateurInfos) throws CompteExiste {
        if(isNull(this.spectateurRepository.findByEmailIs(spectateurInfos.getEmail()))) {

            Spectateur spectateur = new Spectateur(spectateurInfos.getEmail(),spectateurInfos.getNom(),spectateurInfos.getPrenom()) ;
            this.spectateurRepository.save(spectateur) ;
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(spectateur, SpectateurDTO.class);

        } else {
            throw new CompteExiste() ;
        }
    }

    public ResponseEntity<String> supprimerSpectateur(Long idSpectateur) throws CompteInexistant, ExisteBillets {
        if(this.spectateurRepository.findById(idSpectateur).isEmpty()) {
            throw new CompteInexistant();
        }

        Spectateur spectateur = this.spectateurRepository.findById(idSpectateur).get();
        if(!spectateur.getBillets().isEmpty()) {
            throw new ExisteBillets() ;
        }
        this.spectateurRepository.delete(spectateur);
        return new ResponseEntity<>("Suppression ok", HttpStatus.OK) ;
    }
}
