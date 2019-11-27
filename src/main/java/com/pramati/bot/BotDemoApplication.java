package com.pramati.bot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class BotDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(BotDemoApplication.class, args);

	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@EventListener(ApplicationReadyEvent.class)
	public void flaskServerStartup() throws IOException {

		ProcessBuilder pb = new ProcessBuilder("python3.6", "restflask.py");
		Process p = pb.start();
		BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
		System.out.println(in.readLine());

	}

}