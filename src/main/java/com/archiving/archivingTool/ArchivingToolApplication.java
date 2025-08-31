package com.archiving.archivingTool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ArchivingToolApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArchivingToolApplication.class, args);
	}

}
