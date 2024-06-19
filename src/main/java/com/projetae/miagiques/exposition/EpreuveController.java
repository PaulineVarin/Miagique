package com.projetae.miagiques.exposition;

<<<<<<< HEAD
import com.projetae.miagiques.entities.Epreuve;
import com.projetae.miagiques.metier.EpreuveService;
import com.projetae.miagiques.metier.SpectateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
=======
import com.projetae.miagiques.dto.EpreuveDTO;
import com.projetae.miagiques.metier.EpreuveService;
import com.projetae.miagiques.utilities.PersonneExceptions.CompteInexistant;
import com.projetae.miagiques.utilities.PersonneExceptions.RoleIncorrect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
>>>>>>> master
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
<<<<<<< HEAD
import java.util.List;

@RestController
@RequestMapping("/epreuves")
=======

@RestController
@RequestMapping("/epreuve")
>>>>>>> master
public class EpreuveController {

    @Autowired
    private EpreuveService epreuveService;

<<<<<<< HEAD
    @GetMapping("/")
    public Collection<Epreuve> getAllEpreuves(){
        return this.epreuveService.getAll();
    }
=======
    @GetMapping("/consulterEpreuves")
    public Collection<EpreuveDTO> consulterEpreuvesDisponibles() {
        return this.epreuveService.getAllEpreuvesDisponibles() ;
    }

>>>>>>> master
}
