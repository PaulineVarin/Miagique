package com.projetae.miagiques;

import com.projetae.miagiques.dao.*;
import com.projetae.miagiques.dto.*;
import com.projetae.miagiques.metier.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;

import static java.util.Map.entry;

@SpringBootApplication
public class MiagiquesApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MiagiquesApplication.class, args);
	}

	@Autowired
	OrganisateurRepository organisateurRepository;
	@Autowired
	DelegationRepository delegationRepository ;
	@Autowired
	ParticipantRepository participantRepository ;
	@Autowired
	EpreuveRepository epreuveRepository ;
	@Autowired
	InfrastructureSportiveRepository infrastructureSportiveRepository ;
	@Autowired
	BilletRepository billetRepository ;
	@Autowired
	ResultatRepository resultatRepository ;
	@Autowired
	SpectateurRepository spectateurRepository ;
	@Autowired
	ControleurRepository controleurRepository ;

	@Override
	public void run(String... args) throws Exception {
		BilletService billetService = new BilletService(billetRepository, epreuveRepository, spectateurRepository) ;
		ResultatService resultatService = new ResultatService(resultatRepository) ;
		/*--- Creation Infrastructure */
		InfrastructureSportiveService infrastructureSportiveService = new InfrastructureSportiveService(infrastructureSportiveRepository) ;
		InfrastructureSportiveDTO infrastructureSportiveDTO1 = new InfrastructureSportiveDTO("Stade",100,"Toulouse") ;
		InfrastructureSportiveDTO infrastructureSportiveDTO2 = new InfrastructureSportiveDTO("Stade foot",30,"Toulouse") ;
		InfrastructureSportiveDTO infrastructureSportiveDTO3 = new InfrastructureSportiveDTO("Stade tennis ",10,"Toulouse") ;
		InfrastructureSportiveDTO infrastructureSportiveDTO4 = new InfrastructureSportiveDTO("Piscine",5,"Toulouse") ;

		infrastructureSportiveService.creationInfrastructure(infrastructureSportiveDTO1) ;
		infrastructureSportiveService.creationInfrastructure(infrastructureSportiveDTO2) ;
		infrastructureSportiveService.creationInfrastructure(infrastructureSportiveDTO3) ;
		infrastructureSportiveService.creationInfrastructure(infrastructureSportiveDTO4) ;

		/*--- Creation Epreuves */
		EpreuveService epreuveService= new EpreuveService(epreuveRepository,infrastructureSportiveRepository,billetService,resultatService) ;

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.FRANCE);
		LocalDate d1 = LocalDate.parse("2024-12-10", formatter);
		Timestamp ts1 =Timestamp.valueOf(d1.atStartOfDay()) ;

		LocalDate d2 = LocalDate.parse("2024-06-21", formatter);
		Timestamp ts2 =Timestamp.valueOf(d2.atStartOfDay()) ;

		EpreuveDTO epreuveDTO1 = new EpreuveDTO(null,"Tennis",ts1,10,2, 3L) ;
		EpreuveDTO epreuveDTO2 = new EpreuveDTO(null,"Piscine",ts2,5,2, 4L) ;

		epreuveService.creationEpreuve(epreuveDTO1) ;
		epreuveService.creationEpreuve(epreuveDTO2) ;

		/*--- Creation Organisateur */
		OrganisateurService organisateurService = new OrganisateurService(organisateurRepository);
		OrganisateurDTO organisateurDTO = new OrganisateurDTO("VARIN","PAULINE","pauline@mail.com") ;
		organisateurService.creationOrganisateur(organisateurDTO) ;

		/*--- Creation Spectateur */
		SpectateurService spectateurService = new SpectateurService(billetRepository,spectateurRepository) ;
		SpectateurDTO spectateurDTO1 = new SpectateurDTO("MARS","TOTO","toto@mail.com",null) ;
		SpectateurDTO spectateurDTO2 = new SpectateurDTO("AVRIL","TATA","tata@mail.com",null) ;

		spectateurService.creerSpectateur(spectateurDTO1) ;
		spectateurService.creerSpectateur(spectateurDTO2) ;

		/*--- Creation Controleur */
		ControleurService controleurService = new ControleurService(controleurRepository);
		ControleurDTO controleurDTO1 = new ControleurDTO("NAYET","MORGAN","morgan@mail.com") ;
		ControleurDTO controleurDTO2 = new ControleurDTO("VARIN","PAULINE","pauline@mail.com") ;

		controleurService.creerControleur(controleurDTO1) ;
		controleurService.creerControleur(controleurDTO2) ;


		/*--- Creation Delegation */
		DelegationService delegationService = new DelegationService(delegationRepository) ;
		delegationService.creerDelegation("Toulouse") ;
		delegationService.creerDelegation("Paris") ;

		/*--- Creation Participants */
		ParticipantService participantService = new ParticipantService(participantRepository,delegationRepository, epreuveRepository,resultatRepository) ;
		Map<String,Object> infosPa1 = Map.ofEntries(
				entry("nom", "ABRAHAMS"),
				entry("prenom", "HAROLD"),
				entry("email","harold@mail.com"),
				entry("idDelegation",1)
		);
		Map<String,Object> infosPa2 = Map.ofEntries(
				entry("nom", "AGASSI"),
				entry("prenom", "ANDRE"),
				entry("email","andre@mail.com"),
				entry("idDelegation",1)
		);

		Map<String,Object> infosPa3 = Map.ofEntries(
				entry("nom", "BESSON"),
				entry("prenom", "COLETTE"),
				entry("email","colette@mail.com"),
				entry("idDelegation",2)
		);

		Map<String,Object> infosPa4 = Map.ofEntries(
				entry("nom", "BINDA"),
				entry("prenom", "ALFREDO"),
				entry("email","alfredo@mail.com"),
				entry("idDelegation",2)
		);

		participantService.creerParticipant(infosPa1) ;
		participantService.creerParticipant(infosPa2) ;
		participantService.creerParticipant(infosPa3) ;
		participantService.creerParticipant(infosPa4) ;

	}
}
