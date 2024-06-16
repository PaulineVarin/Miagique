package com.projetae.miagiques.dto;

import com.projetae.miagiques.entities.Epreuve;
import org.modelmapper.ModelMapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;



public class ObjectMapperUtils {
    private static final ModelMapper modelMapper = new ModelMapper();
    private ObjectMapperUtils() {}

    /**
     * <p>Note: classeDTO doit posséder un constructeur sans arguments</p>
     *
     * @param listeEpreuves liste d'épreuves
     * @param classeDTO classe de mappage
     * @return liste d'épreuves sous le format EpreuveDTO
     */
    public static List<EpreuveDTO> mapAll(final Collection<Epreuve> listeEpreuves, Class<EpreuveDTO> classeDTO) {
        return listeEpreuves.stream()
                .map(entity -> modelMapper.map(entity, classeDTO))
                .collect(Collectors.toList());
    }

}
