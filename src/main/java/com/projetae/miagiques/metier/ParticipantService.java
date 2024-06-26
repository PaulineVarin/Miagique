package com.projetae.miagiques.metier;

import com.projetae.miagiques.dao.DelegationRepository;
import com.projetae.miagiques.dao.EpreuveRepository;
import com.projetae.miagiques.dao.ParticipantRepository;
import com.projetae.miagiques.dao.ResultatRepository;
import com.projetae.miagiques.dto.ObjectMapperUtils;
import com.projetae.miagiques.dto.ParticipantDTO;
import com.projetae.miagiques.entities.Delegation;
import com.projetae.miagiques.entities.Epreuve;
import com.projetae.miagiques.entities.Participant;
import com.projetae.miagiques.entities.Resultat;
import com.projetae.miagiques.utilities.DelegationExceptions.DelegationInexistante;
import com.projetae.miagiques.utilities.ParticipantExceptions.ParticipantExistant;
import com.projetae.miagiques.utilities.ParticipantExceptions.ParticipantInexistant;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import static java.util.Objects.isNull;

@Service
public class ParticipantService {

    private final ParticipantRepository participantRepository;
    private final DelegationRepository delegationRepository;
    private final EpreuveRepository epreuveRepository;
    private final ResultatRepository resultatRepository;

    public ParticipantService(ParticipantRepository participantRepository, DelegationRepository delegationRepository, EpreuveRepository epreuveRepository, ResultatRepository resultatRepository) {
        this.participantRepository = participantRepository;
        this.delegationRepository = delegationRepository;
        this.epreuveRepository = epreuveRepository;
        this.resultatRepository = resultatRepository;
    }

    public ParticipantDTO creerParticipant(Map<String, Object> pInfos) throws ParticipantExistant, DelegationInexistante {
        String nomParticipant = pInfos.get("nom").toString();
        String prenomParticpant = pInfos.get("prenom").toString();
        String emailParticipant = pInfos.get("email").toString();
        Long idDelegation = ((Number) pInfos.get("idDelegation")).longValue();
        if(!isNull(participantRepository.findByEmail(emailParticipant))) {
            throw new ParticipantExistant();
        }
        if(delegationRepository.findById(idDelegation).isEmpty()) {
            throw new DelegationInexistante();
        }
        Delegation delegation = this.delegationRepository.findById(idDelegation).get();
        Participant participant = new Participant(nomParticipant, prenomParticpant, emailParticipant, delegation);
        this.participantRepository.save(participant);
        ModelMapper modelMapper = new ModelMapper();
        ParticipantDTO participantDTO = modelMapper.map(participant, ParticipantDTO.class);
        return participantDTO;
    }

    public ResponseEntity<String> supprimerParticipant(Long idParticipant) throws ParticipantInexistant {
        if(!this.participantRepository.findById(idParticipant).isPresent()) {
            throw new ParticipantInexistant();
        }
        Participant participant = this.participantRepository.findById(idParticipant).get();
        this.participantRepository.delete(participant);
        return new ResponseEntity<>("Suppression OK", HttpStatus.OK);
    }

    public ResponseEntity<String> inscriptionEpreuve(Long idEpreuve, Long idParticipant) {
        Epreuve epreuve = this.epreuveRepository.findById(idEpreuve).get();
        Participant participant = this.participantRepository.findById(idParticipant).get();
        participant.getEpreuves().add(epreuve);
        this.participantRepository.save(participant);
        epreuve.getParticipants().add(participant);
        this.epreuveRepository.save(epreuve);
        return new ResponseEntity<>("Le participant " + participant.getNom() + " s'est bien inscrit à l'épreuve " + epreuve.getNom() , HttpStatus.OK);
    }

    public ResponseEntity<String> desengagementEpreuve(Long idEpreuve, Long idParticipant) {
        Epreuve epreuve = this.epreuveRepository.findById(idEpreuve).get();
        Participant participant = this.participantRepository.findById(idParticipant).get();
        Timestamp todayDate = new Timestamp(new Date().getTime());

        if(ChronoUnit.DAYS.between(todayDate.toLocalDateTime(), epreuve.getDate().toLocalDateTime()) > 10) {
            epreuve.getParticipants().remove(participant);
            this.epreuveRepository.save(epreuve);
            participant.getEpreuves().remove(epreuve);
            this.participantRepository.save(participant);
            return new ResponseEntity<>("Desengagement OK", HttpStatus.OK);
        } else {
            Resultat resultat = new Resultat(true);
            resultat.setParticipant(participant);
            resultat.setEpreuve(epreuve);
            this.resultatRepository.save(resultat);
            participant.getResultats().add(resultat);
            this.participantRepository.save(participant);
            return new ResponseEntity<>("Desengagement avec forfait OK", HttpStatus.OK);
        }
    }

    public Collection<ParticipantDTO> listerParticipant() {
        Collection<Participant> listeParticipants = new ArrayList<>();
        this.participantRepository.findAll().forEach(listeParticipants::add);
        return ObjectMapperUtils.mapAllParticipants(listeParticipants, ParticipantDTO.class);
    }
}
