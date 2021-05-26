package com.projectpokerrest.pokerrest;

import com.projectpokerrest.pokerrest.model.Authority;
import com.projectpokerrest.pokerrest.model.StatoUtente;
import com.projectpokerrest.pokerrest.model.User;
import com.projectpokerrest.pokerrest.security.service.AuthorityService;
import com.projectpokerrest.pokerrest.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

import static com.projectpokerrest.pokerrest.model.AuthorityName.*;

@SpringBootApplication
public class PokerrestApplication implements CommandLineRunner {

	@Autowired
	private AuthorityService authorityService;

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(PokerrestApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (authorityService.cercaPerNome(ROLE_ADMIN) == null) {
			authorityService.inserisciNuovo(new Authority(ROLE_ADMIN));
		}

		if (authorityService.cercaPerNome(ROLE_PLAYER) == null) {
			authorityService.inserisciNuovo(new Authority(ROLE_PLAYER));
		}

		if (authorityService.cercaPerNome(ROLE_SPECIAL_PLAYER) == null) {
			authorityService.inserisciNuovo(new Authority(ROLE_SPECIAL_PLAYER));
		}

		if (userService.findByUsername("admin") == null) {
			User admin = new User("admin", passwordEncoder.encode("admin"),"Mario", "Rossi",new Date());
			admin.setStato(StatoUtente.ATTIVO);
			admin.setEmail("jwhbhwef@weyfg3.dkdf");
			admin.setCreditoAccumulato(0.0);
			admin.setEsperienzaAccumulata(0.0);
			userService.inserisciNuovo(admin);
		}
	}
}
