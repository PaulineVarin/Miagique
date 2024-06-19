package com.projetae.miagiques.metier;

import com.projetae.miagiques.dao.EpreuveRepository;
import com.projetae.miagiques.dao.InfrastructureSportiveRepository;
import com.projetae.miagiques.dto.EpreuveDTO;
import com.projetae.miagiques.dto.ObjectMapperUtils;
import com.projetae.miagiques.entities.Billet;
import com.projetae.miagiques.entities.Epreuve;
import com.projetae.miagiques.entities.InfrastructureSportive;
import com.projetae.miagiques.entities.Resultat;
import com.projetae.miagiques.utilities.EpreuveExceptions.CapaciteEpreuveSuperieur;
import com.projetae.miagiques.utilities.EpreuveExceptions.EpreuveExiste;
import com.projetae.miagiques.utilities.EpreuveExceptions.EpreuveInexistante;
import com.projetae.miagiques.utilities.InfrastructureSportiveExceptions.InfrastructureSportiveInexistante;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static java.util.Objects.isNull;

@Service
public class EpreuveService {
    private final EpreuveRepository epreuveRepository ;

    private final InfrastructureSportiveRepository infrastructureSportiveRepository ;

    private final BilletService billetService ;

    private final ResultatService resultatService ;

    public EpreuveService(EpreuveRepository epreuveRepository, InfrastructureSportiveRepository infrastructureSportiveRepository, BilletService billetService, ResultatService resultatService) {
        this.epreuveRepository = epreuveRepository;
        this.infrastructureSportiveRepository = infrastructureSportiveRepository;
        this.billetService = billetService;
        this.resultatService = resultatService;
    }

    public Collection<EpreuveDTO> consulterEpreuves() {
        Collection<Epreuve> epreuveliste = new ArrayList<>() ;
        this.epreuveRepository.findAll().forEach(epreuveliste::add);
        return ObjectMapperUtils.mapAllEpreuves(epreuveliste,EpreuveDTO.class);
    }

    public EpreuveDTO creationEpreuve(EpreuveDTO ep) throws EpreuveExiste, InfrastructureSportiveInexistante, CapaciteEpreuveSuperieur {

        if(isNull(this.epreuveRepository.findByNomIs(ep.getNom()))) {

            if (this.infrastructureSportiveRepository.findById(ep.getInfrastructureSportiveId()).isEmpty()) {
                throw new InfrastructureSportiveInexistante() ;
            }

            InfrastructureSportive i = this.infrastructureSportiveRepository.findById(ep.getInfrastructureSportiveId()).get() ;
            if(ep.getNbPlacesSpectateur() > i.getCapacite()) {
                throw new CapaciteEpreuveSuperieur() ;
            }

            Epreuve epreuve = new Epreuve(ep.getNom(),ep.getDate(),ep.getNbPlacesSpectateur(),ep.getNbParticipants(),i) ;
            this.epreuveRepository.save(epreuve);
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(epreuve, EpreuveDTO.class);
        }
        else {
            throw new EpreuveExiste() ;
        }
    }

    public EpreuveDTO miseAJourEpreuve(EpreuveDTO epUpdate, Long idEpreuve) throws EpreuveInexistante, InfrastructureSportiveInexistante, CapaciteEpreuveSuperieur, EpreuveExiste {
        if(this.epreuveRepository.findById(idEpreuve).isEmpty()) {
            throw new EpreuveInexistante() ;
        }

        Epreuve e = this.epreuveRepository.findById(idEpreuve).get() ;

        if(!this.epreuveRepository.findByNomIsAndIdEpreuveIsNot(epUpdate.getNom(), idEpreuve).isEmpty()) {
            throw new EpreuveExiste() ;
        }


        if(this.infrastructureSportiveRepository.findById(epUpdate.getInfrastructureSportiveId()).isEmpty()) {
            throw new InfrastructureSportiveInexistante() ;
        }
        InfrastructureSportive i = this.infrastructureSportiveRepository.findById(epUpdate.getInfrastructureSportiveId()).get();
        if(epUpdate.getNbPlacesSpectateur() > i.getCapacite()) {
            throw new CapaciteEpreuveSuperieur() ;
        }
        e.updateEpreuve(epUpdate,i) ;
        this.epreuveRepository.save(e) ;
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(e, EpreuveDTO.class);
    }

    public ResponseEntity<String> supprimerEpreuve(Long idEpreuve) throws EpreuveInexistante {
        if(this.epreuveRepository.findById(idEpreuve).isEmpty()) {
            throw new EpreuveInexistante() ;
        }
        Epreuve e = this.epreuveRepository.findById(idEpreuve).get() ;
        Collection<Billet> listeBillets = e.getListeBillets() ;
        this.billetService.supprimerBillets(listeBillets) ;

        Collection<Resultat> listeResultats = e.getListeResultats() ;
        this.resultatService.supprimerResultats(listeResultats) ;

        this.epreuveRepository.deleteById(idEpreuve);
        return new ResponseEntity<>("Suppression ok", HttpStatus.OK) ;


    }

    public Collection<EpreuveDTO> getAllEpreuvesDisponibles() {
        Collection<Epreuve> epreuvesDisponibles = new ArrayList<>() ;
        this.epreuveRepository.findAll().forEach(epreuvesDisponibles::add);
        Timestamp todayDate = new Timestamp(new Date().getTime());
        for (Epreuve ep : epreuvesDisponibles) {
            if(ep.getParticipants().size() < ep.getNbParticipants() && ChronoUnit.DAYS.between(todayDate.toLocalDateTime(), ep.getDate().toLocalDateTime()) > 10) {
                epreuvesDisponibles.add(ep);
            }
        }

        return ObjectMapperUtils.mapAllEpreuves(epreuvesDisponibles,EpreuveDTO.class);
    }
}
