package rs.ac.uns.ftn.bachelor_thesis;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import rs.ac.uns.ftn.bachelor_thesis.dto.RegisterInfoDTO;
import rs.ac.uns.ftn.bachelor_thesis.model.Player;
import rs.ac.uns.ftn.bachelor_thesis.model.Role;
import rs.ac.uns.ftn.bachelor_thesis.model.User;
import rs.ac.uns.ftn.bachelor_thesis.security.TokenUtil;
import rs.ac.uns.ftn.bachelor_thesis.service.PlayerService;
import rs.ac.uns.ftn.bachelor_thesis.service.UserService;
import rs.ac.uns.ftn.bachelor_thesis.validation.ValidationUtil;

import java.util.ArrayList;

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
	CommandLineRunner run(UserService userService, PlayerService playerService) {
		return args -> {
			userService.saveRole(new Role(null, "ROLE_PLAYER"));
			userService.saveRole(new Role(null, "ROLE_MANAGER"));

			/*
			userService.saveUser(new User(null, "John", "Doe", "john@gmail.com", "1234", "060123456", new ArrayList<>()));
			userService.saveUser(new User(null, "John", "Travolta", "johntravolta@gmail.com", "1234", "060123456", new ArrayList<>()));

			userService.addRoleToUser("john@gmail.com", "ROLE_PLAYER");
			userService.addRoleToUser("johntravolta@gmail.com", "ROLE_MANAGER");
			 */

			RegisterInfoDTO dto = new RegisterInfoDTO();
			dto.setFirstName("Milos");
			dto.setLastName("Andric");
			dto.setPassword("1234");
			dto.setEmail("andric8@gmail.com");
			dto.setTelephone("060123456");

			playerService.registerPlayer(dto);
		};
	}
}
