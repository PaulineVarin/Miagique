package com.projetae.miagiques.exposition;

import com.projetae.miagiques.metier.BilletService;
import com.projetae.miagiques.utilities.BilletExceptions.BilletInexistant;
import com.projetae.miagiques.utilities.StatutBillet;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/billets/{emailUtilisateur}")
public class BilletController {

    @Autowired
    private BilletService billetService;




    /**
     * Renvoie si le billet est valide pour l'épreuve ou non
     *
     * @param idBillet  l'identifiant unique d'un billet
     * @param idEpreuve l'identifiant unique d'une épreuve
     * @return false si le billet n'est pas valide ou si il n'est pas lié à l'épreuve
     * true sinon
     * @throws BilletInexistant l'identifiant précisé n'existe pas dans la base de données
     */
    @GetMapping({"/{idBillet}/{idEpreuve}/validite"})
    public ResponseEntity<Boolean> valideUnBillet(@PathVariable("idBillet") Long idBillet, @PathVariable("idEpreuve") Long idEpreuve)
            throws BilletInexistant {

        StatutBillet etat;
        boolean isBilletValide;
        Long idEpreuveBillet;

        etat = this.billetService.getEtatBillet(idBillet);
        isBilletValide = StatutBillet.VALIDE == etat;

        if (!isBilletValide) {
            return ResponseEntity.ok(isBilletValide);
        }

        try {
            idEpreuveBillet = this.billetService.getIdEpreuveBillet(idBillet);
        } catch (BilletInexistant e) {
            throw new BilletInexistant(HttpStatus.BAD_GATEWAY);
        }

        isBilletValide = idEpreuve == idEpreuveBillet;

        return ResponseEntity.ok(isBilletValide);
    }
}
