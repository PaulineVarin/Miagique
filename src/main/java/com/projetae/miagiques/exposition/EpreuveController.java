package com.projetae.miagiques.exposition;

import com.projetae.miagiques.entities.Epreuve;
import com.projetae.miagiques.metier.EpreuveService;
import com.projetae.miagiques.metier.SpectateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/epreuves")
public class EpreuveController {

    @Autowired
    private EpreuveService epreuveService;

    @GetMapping("/")
    public Collection<Epreuve> getAllEpreuves(){
        return this.epreuveService.getAll();
    }
}
