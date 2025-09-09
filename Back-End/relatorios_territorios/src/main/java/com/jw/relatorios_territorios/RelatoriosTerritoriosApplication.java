package com.jw.relatorios_territorios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class RelatoriosTerritoriosApplication {

	public static void main(String[] args) {
		SpringApplication.run(RelatoriosTerritoriosApplication.class, args);
	}

}
