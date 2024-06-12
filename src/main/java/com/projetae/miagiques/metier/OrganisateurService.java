package com.projetae.miagiques.metier;

import com.projetae.miagiques.dao.EpreuveRepository;
import com.projetae.miagiques.dao.InfrastructureSportiveRepository;
import com.projetae.miagiques.entities.Epreuve;
import com.projetae.miagiques.entities.InfrastructureSportive;
import com.projetae.miagiques.utilities.EpreuveExceptions.CapaciteEpreuveSuperieur;
import com.projetae.miagiques.utilities.EpreuveExceptions.EpreuveExiste;
import com.projetae.miagiques.utilities.EpreuveExceptions.EpreuveInexistante;
import com.projetae.miagiques.utilities.InfrastructureSportiveExceptions.InfrastructureSportiveInexistant;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

import static java.util.Objects.isNull;

@Service
public class OrganisateurService {

    private final EpreuveRepository epreuveRepository ;
    private final InfrastructureSportiveRepository infrastructureSportiveRepository ;

    public OrganisateurService(EpreuveRepository epreuveRepository, InfrastructureSportiveRepository infrastructureSportiveRepository) {
        this.epreuveRepository = epreuveRepository;
        this.infrastructureSportiveRepository = infrastructureSportiveRepository;
    }

    public Epreuve creationEpreuve(Map<String, Object> ep) throws EpreuveExiste, InfrastructureSportiveInexistant, CapaciteEpreuveSuperieur, ParseException {

        String nomEpreuve = ep.get("nom").toString() ;
        Long idInfrastructure = ((Number) ep.get("idInfrastructure")).longValue() ;
        int nbPlacesSpectateurs = (int) ep.get("nbPlacesSpectateur") ;

        if(isNull(this.epreuveRepository.findByNomIs(nomEpreuve))) {
            if (this.infrastructureSportiveRepository.findById(idInfrastructure).isEmpty()) {
                throw new InfrastructureSportiveInexistant() ;
            }

            InfrastructureSportive i = this.infrastructureSportiveRepository.findById(idInfrastructure).get() ;
            if(nbPlacesSpectateurs > i.getCapacite()) {
                throw new CapaciteEpreuveSuperieur() ;
            }

            int nbParticipants = (int) ep.get("nbParticipants") ;
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            Date d = df.parse(ep.get("date").toString()) ;
            Timestamp t = new Timestamp(d.getTime()) ;

            Epreuve epreuve = new Epreuve(nomEpreuve,t,nbPlacesSpectateurs,nbParticipants,i) ;
            return this.epreuveRepository.save(epreuve);
        }
        else {
            throw new EpreuveExiste() ;
        }
    }

    public void updateEpreuve(Map<String, Object> epUpdate, Epreuve e, InfrastructureSportive i) throws ParseException {
        String nome = epUpdate.get("nom").toString();

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date d = df.parse(epUpdate.get("date").toString()) ;
        Timestamp t = new Timestamp(d.getTime()) ;

        int nbPlacesS = (int) epUpdate.get("nbPlacesSpectateur") ;
        int nbParticipants = (int) epUpdate.get("nbParticipants") ;

        e.setNom(nome);
        e.setDate(t);
        e.setNbPlacesSpectateur(nbPlacesS);
        e.setNbParticipants(nbParticipants);
        e.setInsfrastructureSportive(i);
    }

    public Epreuve miseAJourEpreuve(Map<String, Object> epUpdate, Long idEpreuve) throws EpreuveInexistante, InfrastructureSportiveInexistant, CapaciteEpreuveSuperieur, ParseException {
        if(this.epreuveRepository.findById(idEpreuve).isEmpty()) {
            throw new EpreuveInexistante() ;
        }
        Epreuve e = this.epreuveRepository.findById(idEpreuve).get() ;

        Long idInfrastructure = ((Number)epUpdate.get("idInfrastructure")).longValue() ;
        if(this.infrastructureSportiveRepository.findById(idInfrastructure).isEmpty()) {
            throw new InfrastructureSportiveInexistant() ;
        }
        InfrastructureSportive i = this.infrastructureSportiveRepository.findById(idInfrastructure).get();
        int nbPlacesSpectateurs = (int) epUpdate.get("nbPlacesSpectateur") ;
        if(nbPlacesSpectateurs > i.getCapacite()) {
            throw new CapaciteEpreuveSuperieur() ;
        }
        updateEpreuve(epUpdate, e,i) ;
        return this.epreuveRepository.save(e) ;
    }
}
