package com.literalura.challenge;

import com.literalura.challenge.principal.Principal;
import com.literalura.challenge.repository.AutorRepository;
import com.literalura.challenge.repository.LibrosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChallengeApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(ChallengeApplication.class, args);
	}

	@Autowired
	private LibrosRepository repository;
	@Autowired
	private AutorRepository autorRepository;

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repository,autorRepository);
		principal.mostrar();

	}
}
