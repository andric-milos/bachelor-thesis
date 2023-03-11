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
}
