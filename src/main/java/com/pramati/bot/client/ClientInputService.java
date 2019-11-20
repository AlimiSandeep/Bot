package com.pramati.bot.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.pramati.bot.util.IntentExtractor;

@Service
public class ClientInputService {

	@Autowired
	private ClientGreeting greeting;

	@Autowired
	private IntentExtractor intentExtractor;

	@Autowired
	private ClientDoctorService clientDoctorService;

	@Autowired
	private ClientSlotService clientSlotService;

	@Autowired
	private ClientAppointmentService clientAppointmentService;

	private static BufferedReader reader;

	public String getClientInput() throws IOException {

		System.out.print("User:");
		String input = reader.readLine();

		return input;
	}

	/**
	 * Triggered when application starts
	 * 
	 * @throws IOException
	 * @throws ParseException
	 */
	@EventListener(ApplicationReadyEvent.class)
	public void onStart() throws IOException, ParseException {
		reader = new BufferedReader(new InputStreamReader(System.in));

		mainMethod();
	}

	public void mainMethod() throws IOException, ParseException {

		System.out.println("Start the conversation :");
		System.out.println("Bot: Hello, how can I help you");
		String input;

		do {
			System.out.print("User:");
			input = reader.readLine();

			String intent = intentExtractor.getIntent(input);

			switch (intent) {

			case "doctors":
				System.out.println("Bot :" + clientDoctorService.doctorIntent(input));
				break;

			case "slots":
				clientSlotService.slotIntent(input);
				break;

			case "appointment":
				System.out.println(clientAppointmentService.appointmentIntent(input));
				break;

			case "greet":
				System.out.println("Bot :" + greeting.greetIntent());
				break;

			case "goodbye":
				System.out.println("Bot :" + greeting.byeIntent());
				System.exit(0);

			default:
				System.out.println("In default method");
				break;
			}

		} while (!input.equalsIgnoreCase("exit"));

	}

}
