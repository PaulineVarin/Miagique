package com.projetae.miagiques.exposition;

import com.projetae.miagiques.dto.ParticipantDTO;
import com.projetae.miagiques.metier.ParticipantService;
import com.projetae.miagiques.utilities.DelegationExceptions.DelegationInexistante;
import com.projetae.miagiques.utilities.ParticipantExceptions.ParticipantExistant;
import com.projetae.miagiques.utilities.ParticipantExceptions.ParticipantInexistant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/participant")
public class ParticipantController {
    @Autowired
    private ParticipantService participantService;

    /**
     *
     * @param participantInfos JSON qui contient les informations d'un participant.
     *                         Il doit posséder les attributs suivants : nom, prenom, email, idDelegation
     * @throws DelegationInexistante Si on tente de créer un participant avec une délégation qui n'existe pas
     * @throws ParticipantExistant Si on essaie de créer un participant avec un email déjà utilisé
     * @return le participant ajouté
     */
    @PostMapping("/creationParticipant")
    public ParticipantDTO creerParticipant(@RequestBody Map<String, Object> participantInfos) throws DelegationInexistante, ParticipantExistant {
        return this.participantService.creerParticipant(participantInfos);
    }

    /**
     *
     * @param idParticipant qui représente l'id du participant que l'on souhaite supprimer
     * @throws ParticipantInexistant si on tente de supprimer un participant qui n'existe pas
     */
    @DeleteMapping("/suppressionParticipant")
    public void supprimerParticipant(@RequestBody Long idParticipant) throws ParticipantInexistant {
        this.participantService.supprimerParticipant(idParticipant);
    }

}
