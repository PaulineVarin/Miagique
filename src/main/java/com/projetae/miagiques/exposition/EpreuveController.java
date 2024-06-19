package com.projetae.miagiques.exposition;

import com.projetae.miagiques.dto.EpreuveDTO;
import com.projetae.miagiques.metier.EpreuveService;
import com.projetae.miagiques.utilities.PersonneExceptions.CompteInexistant;
import com.projetae.miagiques.utilities.PersonneExceptions.RoleIncorrect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/epreuve")
public class EpreuveController {

    @Autowired
    private EpreuveService epreuveService;

    @GetMapping("/consulterEpreuves")
    public Collection<EpreuveDTO> consulterEpreuvesDisponibles() {
        return this.epreuveService.getAllEpreuvesDisponibles() ;
    }

}
