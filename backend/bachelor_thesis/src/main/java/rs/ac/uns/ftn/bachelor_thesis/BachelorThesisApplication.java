package rs.ac.uns.ftn.bachelor_thesis;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import rs.ac.uns.ftn.bachelor_thesis.dto.CreateGroupDTO;
import rs.ac.uns.ftn.bachelor_thesis.dto.NewAppointmentDTO;
import rs.ac.uns.ftn.bachelor_thesis.dto.RegisterInfoDTO;
import rs.ac.uns.ftn.bachelor_thesis.model.*;
import rs.ac.uns.ftn.bachelor_thesis.security.CustomCorsFilter;
import rs.ac.uns.ftn.bachelor_thesis.security.TokenUtil;
import rs.ac.uns.ftn.bachelor_thesis.service.*;
import rs.ac.uns.ftn.bachelor_thesis.validation.ValidationUtil;

import java.util.*;

@SpringBootApplication
public class BachelorThesisApplication {

	public static void main(String[] args) {
		SpringApplication.run(BachelorThesisApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	TokenUtil tokenUtil() {
		return new TokenUtil();
	}

	@Bean
	ValidationUtil validationUtil() {
		return new ValidationUtil();
	}

	@Bean
	WebMvcConfigurer webMvcConfigurer() {
		return new CustomCorsFilter();
	}

	@Bean
	CommandLineRunner run(UserService userService,
						  PlayerService playerService,
						  ManagerService managerService,
						  GroupService groupService,
						  AppointmentService appointmentService) {
		return args -> {
			userService.saveRole(new Role(null, "ROLE_PLAYER"));
			userService.saveRole(new Role(null, "ROLE_MANAGER"));

			RegisterInfoDTO dto = new RegisterInfoDTO("Milos", "Andric", "andric8@gmail.com", "1234", "060123456", "player");

			playerService.registerPlayer(dto);

			dto = new RegisterInfoDTO("John", "Doe", "johndoe@gmail.com", "1234", "060111111", "player");
			playerService.registerPlayer(dto);
			dto = new RegisterInfoDTO("John", "Travolta", "travolta@gmail.com", "1234", "060112111", "player");
			playerService.registerPlayer(dto);
			dto = new RegisterInfoDTO("Paul", "Scholes", "scholes@gmail.com", "1234", "060111151", "player");
			playerService.registerPlayer(dto);
			dto = new RegisterInfoDTO("Karim", "Benzema", "benzema@gmail.com", "1234", "060114111", "player");
			playerService.registerPlayer(dto);
			dto = new RegisterInfoDTO("Kevin", "De Bruyne", "kdb@gmail.com", "1234", "060111111", "player");
			playerService.registerPlayer(dto);
			dto = new RegisterInfoDTO("Luka", "Modrić", "lukamodric@gmail.com", "1234", "060112111", "player");
			playerService.registerPlayer(dto);
			dto = new RegisterInfoDTO("Petar", "Petrovic", "ppetrovic@gmail.com", "1234", "060111151", "player");
			playerService.registerPlayer(dto);
			dto = new RegisterInfoDTO("Marko", "Markovic", "markovic@gmail.com", "1234", "060114111", "player");
			playerService.registerPlayer(dto);
			dto = new RegisterInfoDTO("Mitar", "Mitrovic", "mitar@gmail.com", "1234", "060111111", "player");
			playerService.registerPlayer(dto);
			dto = new RegisterInfoDTO("Aleksandar", "Mitrovic", "mitrovic@gmail.com", "1234", "060112111", "player");
			playerService.registerPlayer(dto);
			dto = new RegisterInfoDTO("Benjamin", "Sesko", "sesko@gmail.com", "1234", "060111151", "player");
			playerService.registerPlayer(dto);
			dto = new RegisterInfoDTO("Petar", "Popovic", "petarpopovic@gmail.com", "1234", "060114111", "player");
			playerService.registerPlayer(dto);
			dto = new RegisterInfoDTO("Nikola", "Nikolic", "nikolic@gmail.com", "1234", "060111111", "player");
			playerService.registerPlayer(dto);
			dto = new RegisterInfoDTO("Aleksandar", "Pesic", "apesic@gmail.com", "1234", "060112111", "player");
			playerService.registerPlayer(dto);
			dto = new RegisterInfoDTO("Nikola", "Stojanovic", "nstojanovic@gmail.com", "1234", "060111151", "player");
			playerService.registerPlayer(dto);
			dto = new RegisterInfoDTO("Vuk", "Vukovic", "vukvuk@gmail.com", "1234", "060114111", "player");
			playerService.registerPlayer(dto);
			dto = new RegisterInfoDTO("Vladimir", "Pantic", "vpantic@gmail.com", "1234", "060111111", "player");
			playerService.registerPlayer(dto);
			dto = new RegisterInfoDTO("Jovan", "Jovanovic", "jovanjovanovic@gmail.com", "1234", "060112111", "player");
			playerService.registerPlayer(dto);
			dto = new RegisterInfoDTO("Jovan", "Vukotic", "jvukotic@gmail.com", "1234", "060111151", "player");
			playerService.registerPlayer(dto);
			dto = new RegisterInfoDTO("Petar", "Pantic", "petarpantic@gmail.com", "1234", "060114111", "player");
			playerService.registerPlayer(dto);

			dto = new RegisterInfoDTO("Nikolina", "Petkovic", "ninapetkovic@gmail.com", "1234", "060119811", "manager");
			managerService.registerManager(dto);
			dto = new RegisterInfoDTO("Marijana", "Marijanovic", "marijana@gmail.com", "1234", "060118111", "manager");
			managerService.registerManager(dto);
			dto = new RegisterInfoDTO("Marko", "Marinkovic", "marinkovicm@gmail.com", "1234", "060119811", "manager");
			managerService.registerManager(dto);
			dto = new RegisterInfoDTO("Miloš", "Andrić", "andric10@gmail.com", "1234", "060119811", "manager");
			managerService.registerManager(dto);

			// Creating a group
			Optional<Player> MilosAndric = playerService.getPlayerByEmail("andric8@gmail.com");
			Set<String> playersEmails = new HashSet<>();
			Collections.addAll(playersEmails, "vpantic@gmail.com", "vukvuk@gmail.com", "petarpopovic@gmail.com", "travolta@gmail.com", "scholes@gmail.com", "benzema@gmail.com", "jvukotic@gmail.com");
			CreateGroupDTO createGroupDTO = new CreateGroupDTO("Fudbal četvrtkom u 20h", playersEmails);
			groupService.createGroup(createGroupDTO, MilosAndric.get());

			// Creating a group
			Optional<Player> JohnDoe = playerService.getPlayerByEmail("johndoe@gmail.com");
			playersEmails = new HashSet<>();
			Collections.addAll(playersEmails, "andric8@gmail.com", "nikolic@gmail.com", "markovic@gmail.com");
			createGroupDTO = new CreateGroupDTO("Joga Bonito", playersEmails);
			groupService.createGroup(createGroupDTO, JohnDoe.get());

			// Adding appointments to the group
			NewAppointmentDTO appointmentDTO = new NewAppointmentDTO();
			appointmentDTO.setGroupId(2L);
			appointmentDTO.setDate(1671130800000L);
			appointmentDTO.setAddress("Hajduk Veljkova 4");
			appointmentDTO.setPrivacy("private");
			appointmentDTO.setCapacity(10);
			appointmentDTO.setPrice(3800.00);
			appointmentService.createAppointment(appointmentDTO);

			// Adding appointments to the group
			appointmentDTO = new NewAppointmentDTO();
			appointmentDTO.setGroupId(1L);
			appointmentDTO.setDate(1671130800000L);
			appointmentDTO.setAddress("Šafarikova 10");
			appointmentDTO.setPrivacy("private");
			appointmentDTO.setCapacity(10);
			appointmentDTO.setPrice(3800.00);
			appointmentService.createAppointment(appointmentDTO);

			// Adding appointments to the group
			appointmentDTO = new NewAppointmentDTO();
			appointmentDTO.setGroupId(2L);
			appointmentDTO.setDate(1670698800000L);
			appointmentDTO.setAddress("Bulevara Cara Lazara 83");
			appointmentDTO.setPrivacy("public");
			appointmentDTO.setCapacity(12);
			appointmentDTO.setPrice(4000.00);
			appointmentService.createAppointment(appointmentDTO);

			// Adding appointments to the group
			appointmentDTO = new NewAppointmentDTO();
			appointmentDTO.setGroupId(1L);
			appointmentDTO.setDate(1671735600000L);
			appointmentDTO.setAddress("Braće Ribnikar 7");
			appointmentDTO.setPrivacy("public");
			appointmentDTO.setCapacity(12);
			appointmentDTO.setPrice(3000.00);
			appointmentService.createAppointment(appointmentDTO);

			// Adding appointments to the group
			appointmentDTO = new NewAppointmentDTO();
			appointmentDTO.setGroupId(1L);
			appointmentDTO.setDate(1672340400000L);
			appointmentDTO.setAddress("Mičurinova 12");
			appointmentDTO.setPrivacy("public");
			appointmentDTO.setCapacity(12);
			appointmentDTO.setPrice(3400.00);
			appointmentService.createAppointment(appointmentDTO);

			// Adding appointments to the group
			appointmentDTO = new NewAppointmentDTO();
			appointmentDTO.setGroupId(2L);
			appointmentDTO.setDate(1672340400000L);
			appointmentDTO.setAddress("Bulevar Evrope 78");
			appointmentDTO.setPrivacy("public");
			appointmentDTO.setCapacity(12);
			appointmentDTO.setPrice(3400.00);
			appointmentService.createAppointment(appointmentDTO);
		};
	}
}
