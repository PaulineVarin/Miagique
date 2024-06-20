package com.projetae.miagiques.dto;

import com.projetae.miagiques.entities.*;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
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
    public static Collection<EpreuveDTO> mapAllEpreuves(final Collection<Epreuve> listeEpreuves, Class<EpreuveDTO> classeDTO) {
        return listeEpreuves.stream()
                .map(entity -> modelMapper.map(entity, classeDTO))
                .collect(Collectors.toList());
    }

    /**
     * <p>Note: classeDTO doit posséder un constructeur sans arguments</p>
     *
     * @param listeStatistiques liste qui contient les statistiques
     * @param classeDTO classe de mappage
     * @return liste qui contient les statistiques sous le format StatistiqueEpreuveDTO
     */
    public static Collection<StatistiqueEpreuveDTO> mapAllStatistiques(final Collection<StatistiqueEpreuve> listeStatistiques, Class<StatistiqueEpreuveDTO> classeDTO) {
        return listeStatistiques.stream()
                .map(entity -> modelMapper.map(entity, classeDTO))
                .collect(Collectors.toList());
    }

    /**
     * <p>Note: classeDTO doit posséder un constructeur sans arguments</p>
     *
     * @param listeInfrastructures liste qui contient les infrastructures
     * @param classeDTO classe de mappage
     * @return liste qui contient les infrastructures sous le format InfrastructureSportiveDTO
     */
    public static Collection<InfrastructureSportiveDTO> mapAllInfrastructures(final Collection<InfrastructureSportive> listeInfrastructures, Class<InfrastructureSportiveDTO> classeDTO) {
        return listeInfrastructures.stream()
                .map(entity -> modelMapper.map(entity, classeDTO))
                .collect(Collectors.toList());
    }


    /**
     * <p>Note: classeDTO doit posséder un constructeur sans arguments</p>
     *
     * @param listeBillets liste qui contient les infrastructures
     * @param classeDTO classe de mappage
     * @return liste qui contient les infrastructures sous le format InfrastructureSportiveDTO
     */
    public static Collection<BilletDTO> mapAllBillets(final Collection<Billet> listeBillets, Class<BilletDTO> classeDTO) {
        Collection<BilletDTO> listeBilletsDTO = new ArrayList<>() ;
        ModelMapper modelMapper;
        modelMapper = new ModelMapper();
        for(Billet b:listeBillets) {
            BilletDTO bi = new BilletDTO();
            modelMapper.map(b,bi);
            bi.setSpectateurId(b.getSpectateur().getId());
            bi.setEpreuveId(b.getEpreuve().getIdEpreuve());
            listeBilletsDTO.add(bi) ;
        }

        return listeBilletsDTO ;

    }


    /**
     * <p>Note: classeDTO doit posséder un constructeur sans arguments</p>
     *
     * @param listeParticipants liste qui contient les participants
     * @param classeDTO classe de mappage
     * @return liste qui contient les participants sous le format ParticipantDTO
     */
    public static Collection<ParticipantDTO> mapAllParticipants(final Collection<Participant> listeParticipants, Class<ParticipantDTO> classeDTO) {
        return listeParticipants.stream()
                .map(entity -> modelMapper.map(entity, classeDTO))
                .collect(Collectors.toList());
    }


}
