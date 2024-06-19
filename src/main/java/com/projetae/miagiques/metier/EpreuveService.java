package com.projetae.miagiques.metier;

import com.projetae.miagiques.dao.EpreuveRepository;
import com.projetae.miagiques.dao.InfrastructureSportiveRepository;
import com.projetae.miagiques.entities.Billet;
import com.projetae.miagiques.entities.Epreuve;
import com.projetae.miagiques.utilities.EpreuveExceptions.EpreuveInexistante;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Optional;
import com.projetae.miagiques.dto.EpreuveDTO;
import com.projetae.miagiques.dto.ObjectMapperUtils;
import com.projetae.miagiques.entities.InfrastructureSportive;
import com.projetae.miagiques.entities.Resultat;
import com.projetae.miagiques.utilities.EpreuveExceptions.CapaciteEpreuveSuperieur;
import com.projetae.miagiques.utilities.EpreuveExceptions.EpreuveExiste;
import com.projetae.miagiques.utilities.InfrastructureSportiveExceptions.InfrastructureSportiveInexistante;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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

        InfrastructureSportive i;
        Epreuve epreuve;
        ModelMapper modelMapper;

        if(!isNull(this.epreuveRepository.findByNomIs(ep.getNom()))) {
            throw new EpreuveExiste();
        }

        if (this.infrastructureSportiveRepository.findById(ep.getInfrastructureSportiveId()).isEmpty()) {
            throw new InfrastructureSportiveInexistante();
        }

        i = this.infrastructureSportiveRepository.findById(ep.getInfrastructureSportiveId()).get() ;
        if(ep.getNbPlacesSpectateur() > i.getCapacite()) {
            throw new CapaciteEpreuveSuperieur();
        }

        epreuve = new Epreuve(ep.getNom(),ep.getDate(),ep.getNbPlacesSpectateur(),ep.getNbParticipants(),i) ;
        this.epreuveRepository.save(epreuve);
        modelMapper = new ModelMapper();
        return modelMapper.map(epreuve, EpreuveDTO.class);
    }

    public EpreuveDTO miseAJourEpreuve(EpreuveDTO epUpdate, Long idEpreuve) throws EpreuveInexistante, InfrastructureSportiveInexistante, CapaciteEpreuveSuperieur, EpreuveExiste {

        Epreuve e;
        InfrastructureSportive i;
        ModelMapper modelMapper;

        if(this.epreuveRepository.findById(idEpreuve).isEmpty()) {
            throw new EpreuveInexistante(HttpStatus.NOT_FOUND);
        }

        e = this.epreuveRepository.findById(idEpreuve).get();

        if(!this.epreuveRepository.findByNomIsAndIdEpreuveIsNot(epUpdate.getNom(), idEpreuve).isEmpty()) {
            throw new EpreuveExiste();
        }

        if(this.infrastructureSportiveRepository.findById(epUpdate.getInfrastructureSportiveId()).isEmpty()) {
            throw new InfrastructureSportiveInexistante();
        }

        i = this.infrastructureSportiveRepository.findById(epUpdate.getInfrastructureSportiveId()).get();
        if(epUpdate.getNbPlacesSpectateur() > i.getCapacite()) {
            throw new CapaciteEpreuveSuperieur();
        }

        e.updateEpreuve(epUpdate,i);
        this.epreuveRepository.save(e);
        modelMapper = new ModelMapper();

        return modelMapper.map(e, EpreuveDTO.class);
    }

    public ResponseEntity<String> supprimerEpreuve(Long idEpreuve) throws EpreuveInexistante {

        Epreuve e;
        Collection<Billet> listeBillets;
        Collection<Resultat> listeResultats;

        if(this.epreuveRepository.findById(idEpreuve).isEmpty()) {
            throw new EpreuveInexistante(HttpStatus.NOT_FOUND) ;
        }

        e = this.epreuveRepository.findById(idEpreuve).get() ;
        listeBillets = e.getListeBillets() ;
        this.billetService.supprimerBillets(listeBillets) ;

        listeResultats = e.getListeResultats() ;
        this.resultatService.supprimerResultats(listeResultats) ;

        this.epreuveRepository.deleteById(idEpreuve);
        return new ResponseEntity<>("Suppression ok", HttpStatus.OK) ;
    }

    public Collection<EpreuveDTO> getAllEpreuvesDisponibles() {

        Collection<Epreuve> epreuves;
        Collection<Epreuve> epreuvesDisponibles;
        Timestamp todayDate;

        epreuves = new ArrayList<>() ;
        this.epreuveRepository.findAll().forEach(epreuves::add);
        
        todayDate = new Timestamp(new Date().getTime());
        epreuvesDisponibles = new ArrayList<>();
        for (Epreuve ep : epreuves) {
            if(ep.getParticipants().size() < ep.getNbParticipants() && ChronoUnit.DAYS.between(todayDate.toLocalDateTime(), ep.getDate().toLocalDateTime()) > 10) {
                epreuvesDisponibles.add(ep);
            }
        }
        return ObjectMapperUtils.mapAllEpreuves(epreuvesDisponibles,EpreuveDTO.class);
    }
}
