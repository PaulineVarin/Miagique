package com.projetae.miagiques.metier;

import com.projetae.miagiques.dao.EpreuveRepository;
import com.projetae.miagiques.dao.ParticipantRepository;
import com.projetae.miagiques.dao.ResultatRepository;
import com.projetae.miagiques.dto.ResultatDTO;
import com.projetae.miagiques.dto.ResultatInfosDTO;
import com.projetae.miagiques.entities.Epreuve;
import com.projetae.miagiques.entities.Participant;
import com.projetae.miagiques.entities.Resultat;
import com.projetae.miagiques.utilities.EpreuveExceptions.EpreuveInexistante;
import com.projetae.miagiques.utilities.ParticipantExceptions.ParticipantInexistant;
import com.projetae.miagiques.utilities.ResultatExceptions.ResultatExistant;
import com.projetae.miagiques.utilities.ResultatExceptions.ResultatTempsNegatif;
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

    public ResponseEntity<ResultatDTO> publierResultat(ResultatInfosDTO resultatInfos) throws EpreuveInexistante, ParticipantInexistant, ResultatExistant, ResultatTempsNegatif {

        Epreuve ep;
        Participant part;
        Resultat res;
        ResultatDTO resDTO;

        if (resultatInfos == null || resultatInfos.getIdEpreuve() == null || resultatInfos.getIdParticipant() == null)
            throw new IllegalArgumentException("resultatInfos or idEpreuve or idParticipant may be null");

        if ( this.epreuveRepository.findById(resultatInfos.getIdEpreuve()).isEmpty() )
            throw new EpreuveInexistante(HttpStatus.NOT_FOUND);

        ep = this.epreuveRepository.findById(resultatInfos.getIdEpreuve()).get();
        if ( this.participantRepository.findById(resultatInfos.getIdParticipant()).isEmpty())
            throw new ParticipantInexistant(HttpStatus.NOT_FOUND);

        part = this.participantRepository.findById(resultatInfos.getIdParticipant()).get();
        if (resultatInfos.getTemps() < 0)
            throw new ResultatTempsNegatif(HttpStatus.BAD_REQUEST);

        //cas où le résultat existe déjà
        if (this.resultatRepository.findByEpreuveAndParticipantAndPosition(ep, part, resultatInfos.getPosition()) != null)
            throw new ResultatExistant(HttpStatus.CONFLICT);

        //cas où le résultat n'existe pas
        res = new Resultat(resultatInfos.getTemps(), resultatInfos.getPoints(), resultatInfos.getPosition(), part, ep);
        part.getResultats().add(res);
        ep.getListeResultats().add(res);

        this.resultatRepository.save(res);
        this.participantRepository.save(part);
        this.epreuveRepository.save(ep);

        resDTO = new ResultatDTO(res.getIdResultat(), resultatInfos.getTemps(), resultatInfos.getPoints(), resultatInfos.getPosition(), res.isForfait(),
                ep.getIdEpreuve(),ep.getNom(), part.getId(),part.getNom());

        return ResponseEntity.ok(resDTO);
    }
}
