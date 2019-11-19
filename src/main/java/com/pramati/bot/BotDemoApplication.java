package com.pramati.bot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class BotDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(BotDemoApplication.class, args);

	}

	@EventListener(ApplicationReadyEvent.class)
	public void flaskServerStartup() throws IOException {

		ProcessBuilder pb = new ProcessBuilder("python3.6", "/home/sandeepa/My Data/source_code/restflask.py");
		Process p = pb.start();
		BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
		System.out.println(in.readLine());

	}

}