package com.projetae.miagiques.metier;

import com.projetae.miagiques.dao.EpreuveRepository;
import com.projetae.miagiques.dao.ParticipantRepository;
import com.projetae.miagiques.dao.ResultatRepository;
import com.projetae.miagiques.dto.ResultatDTO;
import com.projetae.miagiques.entities.Epreuve;
import com.projetae.miagiques.entities.Participant;
import com.projetae.miagiques.entities.Resultat;
import com.projetae.miagiques.utilities.EpreuveExceptions.EpreuveInexistante;
import com.projetae.miagiques.utilities.ParticipantExceptions.ParticipantInexistant;
import com.projetae.miagiques.utilities.ResultatExceptions.ResultatExistant;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

@Service
public class ResultatService {
    private final ResultatRepository resultatRepository;
    private final EpreuveRepository epreuveRepository;
    private final ParticipantRepository participantRepository;

    public ResultatService(ResultatRepository resultatRepository, EpreuveRepository epreuveRepository, ParticipantRepository participantRepository) {
        this.resultatRepository = resultatRepository;
        this.epreuveRepository = epreuveRepository;
        this.participantRepository = participantRepository;
    }

    public void supprimerResultats(Collection<Resultat> listeResultats) {
        for (Resultat r : listeResultats) {
            this.resultatRepository.deleteById(r.getIdResultat());
        }
    }

    public ResponseEntity<ResultatDTO> publierResultat(float temps, int points, int position, Long idEpreuve, Long idParticipant) throws EpreuveInexistante, ParticipantInexistant, ResultatExistant {

        Epreuve ep;
        Participant part;
        Resultat res;
        ResultatDTO resDTO;

        if (idEpreuve == null || idParticipant == null)
            throw new IllegalArgumentException("idEpreuve or idParticipant may be null");

        if ( (ep = this.epreuveRepository.findById(idEpreuve).get()) == null )
            throw new EpreuveInexistante(HttpStatus.NOT_FOUND);

        if ( (part = this.participantRepository.findById(idParticipant).get()) == null )
            throw new ParticipantInexistant(HttpStatus.NOT_FOUND);

        if (temps < 0)
            throw new IllegalArgumentException("Temps may be below 0");

        //cas où le résultat existe déjà
        if (this.resultatRepository.findByEpreuveAndParticipantAndPosition(ep, part, position) != null)
            throw new ResultatExistant(HttpStatus.CONFLICT);

        //cas où le résultat n'existe pas
        res = new Resultat(temps, points, position, part, ep);
        part.getResultats().add(res);
        ep.getListeResultats().add(res);

        this.resultatRepository.save(res);
        this.participantRepository.save(part);
        this.epreuveRepository.save(ep);

        resDTO = new ResultatDTO(res.getIdResultat(), temps, points, position, res.isForfait(),
                                 part.getId(), part.getNom(), ep.getIdEpreuve(), ep.getNom());

        return ResponseEntity.ok(resDTO);
    }
}
