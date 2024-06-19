package com.projetae.miagiques.exposition;

import com.projetae.miagiques.entities.Epreuve;
import com.projetae.miagiques.metier.EpreuveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import com.projetae.miagiques.dto.EpreuveDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collection;

@RestController
@RequestMapping("/epreuves")
public class EpreuveController {

    @Autowired
    private EpreuveService epreuveService;

    @GetMapping("/")
    public Collection<EpreuveDTO> getAllEpreuves(){
        return this.epreuveService.getAllEpreuves();
    }

    @GetMapping("/consulterEpreuves")
    public Collection<EpreuveDTO> consulterEpreuvesDisponibles() {
        return this.epreuveService.getAllEpreuvesDisponibles() ;
    }
}
