package com.pramati.bot.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class ClientInputService {

	@Autowired
	private LemmatizeText lemmatiser;

	@Autowired
	private IntentExtractor intentExtractor;

	@Autowired
	private NameAndTitleExtractor nameAndTitleExtractor;

	@Autowired
	private DoctorService docService;

	@Autowired
	private DateAndTimeExtractor dateAndTimeExtractor;

	@Autowired
	private SlotService slotService;

	@Autowired
	private AppointmentService appointmentService;

	private static BufferedReader reader;

	private static List<String> greetList;

	private static List<String> byeList;

	public ClientInputService() {

		greetList = new ArrayList<String>();
		byeList = new ArrayList<String>();

		greetList.add("Hi");
		greetList.add("Hello");
		greetList.add("Hello,How can i help you");
		greetList.add("Hie,Good morning");

		byeList.add("Bye bye");
		byeList.add("Ciao....Bye");
		byeList.add("Good to chat with you....Bye");
		byeList.add("Hope you are satisfied with our service....Bye");

	}

	public String getClientInput() throws IOException {

		System.out.print("User:");
		String input = reader.readLine();

		return input;
	}

	/**
	 * Triggered when application starts
	 * 
	 * @throws IOException
	 */
	@EventListener(ApplicationReadyEvent.class)
	public void onStart() throws IOException {
//		scan = new Scanner(System.in);
		reader = new BufferedReader(new InputStreamReader(System.in));

		mainMethod();
	}

	public void mainMethod() throws IOException {

		System.out.println("Start the conversation :");
		System.out.println("Bot: Hello, how can I help you");
		String input;

		do {
			System.out.print("User:");
			input = reader.readLine();

			String intent = intentExtractor.getIntent(input);

			switch (intent) {

			case "doctors":
				System.out.println("Bot :" + doctorIntent(input));
				break;

			case "slots":
				slotIntent(input);
				break;

			case "appointment":
				System.out.println(appointmentIntent(input));
				break;

			case "greet":
				System.out.println("Bot :" + greetIntent());
				break;

			case "goodbye":
				System.out.println("Bot :" + byeIntent());
				System.exit(0);

			default:
				System.out.println("In default method");
				break;
			}

		} while (!input.equalsIgnoreCase("exit"));

	}

	public String greetIntent() {
		Random rand = new Random();
		return greetList.get(rand.nextInt(greetList.size()));
	}

	public String byeIntent() {
		Random rand = new Random();
		return byeList.get(rand.nextInt(byeList.size()));
	}

	public String appointmentIntent(String userQuery) throws IOException {
		String docName = getDoctorDetails(userQuery);
		String date = dateAndTimeExtractor.getDate(userQuery);
		String time = dateAndTimeExtractor.getTime(userQuery);

		if (date.equalsIgnoreCase("Date not found")) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			date = dateFormat.format(new Date());
		}

		if (time.equalsIgnoreCase("Time not found")) {
			do {
				System.out.println("Please provide time for the appointment");
				time = reader.readLine();
				time = dateAndTimeExtractor.getTime(time);
//				System.out.println(time);

			} while (time.equalsIgnoreCase("Time not found"));
		}

		return appointmentService.createAppointment(docName, time, date, getPatientDetails());
	}

	public String getPatientDetails() throws IOException {
		String patientName;
		System.out.println("Please enter your name");
		patientName = reader.readLine();
		patientName = nameAndTitleExtractor.getName(patientName);

		if (patientName.equalsIgnoreCase("Name not found")) {
			do {
				System.out.println("Please provide your name correctly");
				patientName = reader.readLine();
				patientName = nameAndTitleExtractor.getName(patientName);
//				System.out.println(name);

			} while (patientName.equalsIgnoreCase("Name not found"));
		}

		return patientName;

	}

	public String getDoctorDetails(String input) throws IOException {
		String name = nameAndTitleExtractor.getName(input);
//		System.out.println(name);
		if (name.equalsIgnoreCase("Name not found")) {
			do {
				System.out.println("Please provide name of the doctor");
				name = reader.readLine();
				name = nameAndTitleExtractor.getName(name);

			} while (name.equalsIgnoreCase("Name not found"));
		}

		return name;

	}

	public String doctorIntent(String userQuery) {

		String name = nameAndTitleExtractor.getName(userQuery);

		if (!name.equalsIgnoreCase("Name not found"))
			return docService.getDoctor(name);
		
		userQuery = lemmatiser.getLemmatizedText(userQuery);
		String specialization = nameAndTitleExtractor.getTitle(userQuery);
		System.out.println(name + " " + specialization);
		if (!specialization.equalsIgnoreCase("Title not found")) {
			return docService.getDoctorsBySpecialization(specialization);
		}

		return docService.getDoctors().toString();
	}

	public void slotIntent(String userQuery) throws IOException {
		String date = dateAndTimeExtractor.getDate(userQuery);

		if (date.equalsIgnoreCase("Date not found")) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			date = dateFormat.format(new Date());
		}

		String docName = getDoctorDetails(userQuery);
		System.out.println(slotService.getAvailableSlots(date, docName));

		System.out.println("Do you want to book appointment with the given details");
		System.out.println("Doctor :" + docName);
		System.out.println("Date :" + date);
		System.out.println("Press [y/n]");
		String choice = reader.readLine();
		if (choice.equalsIgnoreCase("y") || choice.equalsIgnoreCase("yes")) {
			String inputs = date + " " + docName;
			System.out.println(appointmentIntent(inputs));
		}
		return;

	}

}
