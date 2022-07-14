package br.com.cursos.cursos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class CursosApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(CursosApplication.class);

	public static void main(String[] args) {
		LOGGER.info("Mensagem de log: iniciando API de cadastro");
		SpringApplication.run(CursosApplication.class, args);
		LOGGER.info("Mensagem de log: sucesso ao inicar a API de cadastro");

	}

}
