package rs.ac.uns.ftn.bachelor_thesis;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import rs.ac.uns.ftn.bachelor_thesis.dto.RegisterInfoDTO;
import rs.ac.uns.ftn.bachelor_thesis.model.Role;
import rs.ac.uns.ftn.bachelor_thesis.security.CustomCorsFilter;
import rs.ac.uns.ftn.bachelor_thesis.security.TokenUtil;
import rs.ac.uns.ftn.bachelor_thesis.service.PlayerService;
import rs.ac.uns.ftn.bachelor_thesis.service.UserService;
import rs.ac.uns.ftn.bachelor_thesis.validation.ValidationUtil;

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
	CommandLineRunner run(UserService userService, PlayerService playerService) {
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
		};
	}
}
