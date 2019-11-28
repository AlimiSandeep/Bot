package com.pramati.bot.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pramati.bot.service.PatientService;
import com.pramati.bot.util.NameAndTitleExtractor;
import com.pramati.bot.util.NumberValidator;

@Service
public class ClientRegistration {

	@Autowired
	private NameAndTitleExtractor nameAndTitleExtractor;

	@Autowired
	private NumberValidator numberValidator;

	@Autowired
	private PatientService patientService;

	private static BufferedReader reader;

	static {
		reader = new BufferedReader(new InputStreamReader(System.in));
	}

	private int counter = 0;

	public String getPatientName() throws IOException {
		String patientName;
		System.out.println("Please enter your name");
		patientName = reader.readLine();
		patientName = nameAndTitleExtractor.getName(patientName);

		if (patientName.equalsIgnoreCase("Name not found")) {
			counter = 0;
			do {
				System.out.println("Please provide your name correctly");
				patientName = reader.readLine();
				patientName = nameAndTitleExtractor.getName(patientName);
				counter++;

			} while (patientName.equalsIgnoreCase("Name not found") && counter < 2);

			if (patientName.equalsIgnoreCase("Name not found"))
				return null;
		}

		return patientName;

	}

	public String getPatientContact() throws IOException {
		String contact;
		System.out.println("Please enter your mobile number");
		contact = reader.readLine();
		boolean flag = numberValidator.validateContactNumber(contact);
		if (!flag) {
			counter = 0;
			do {
				System.out.println("Please enter a valid mobile number--Ex:9912399123");
				contact = reader.readLine();
				flag = numberValidator.validateContactNumber(contact);
				counter++;

			} while (!flag && counter < 2);

			if (!flag)
				return null;

		}

		return contact;

	}

	public String getPatientDetails() throws IOException {
		String name = getPatientName();
		String contact = getPatientContact();

		if (name != null && contact != null) {
			if (patientService.newPatient(name, Long.parseLong(contact)))
				return name;
		}

		return null;

	}
}
