package com.projetae.miagiques.exposition;

import com.projetae.miagiques.dto.ResultatDTO;
import com.projetae.miagiques.entities.Organisateur;
import com.projetae.miagiques.entities.Personne;
import com.projetae.miagiques.entities.Resultat;
import com.projetae.miagiques.metier.PersonneService;
import com.projetae.miagiques.metier.ResultatService;
import com.projetae.miagiques.utilities.EpreuveExceptions.EpreuveInexistante;
import com.projetae.miagiques.utilities.ParticipantExceptions.ParticipantInexistant;
import com.projetae.miagiques.utilities.PersonneExceptions.CompteInexistant;
import com.projetae.miagiques.utilities.PersonneExceptions.RoleIncorrect;
import com.projetae.miagiques.utilities.ResultatExceptions.ResultatExistant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/resultats/{emailUtilisateur}")
public class ResultatControlller {

    @Autowired
    private ResultatService resultatService;
    @Autowired
    private PersonneService personneService;

    @PostMapping("PublierResultat")
    public ResponseEntity<ResultatDTO> publierResultat(@PathVariable("emailUtilisateur") String email, @RequestBody float temps, @RequestBody int points, @RequestBody int position, @RequestBody Long idEpreuve, @RequestBody Long idParticipant)
            throws RoleIncorrect, CompteInexistant, EpreuveInexistante, ResultatExistant, ParticipantInexistant {

        this.testerRole(email, Organisateur.class);

        return this.resultatService.publierResultat(temps, points, position, idEpreuve, idParticipant);
    }

    public <T> boolean testerRole(String mail, Class<T> type) throws CompteInexistant, RoleIncorrect {
        ArrayList<Personne> listeRolesPersonne = new ArrayList<>(this.personneService.seConnecter(mail)) ;

        if (listeRolesPersonne.stream().noneMatch(p -> type.isInstance(p))){
            throw new RoleIncorrect();
        }
        return true ;
    }
}
