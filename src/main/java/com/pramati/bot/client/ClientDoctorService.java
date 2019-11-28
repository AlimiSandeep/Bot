package com.pramati.bot.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pramati.bot.service.DoctorService;
import com.pramati.bot.util.LemmatizeText;
import com.pramati.bot.util.NameAndTitleExtractor;

@Service
public class ClientDoctorService {

	@Autowired
	private DoctorService docService;

	@Autowired
	private LemmatizeText lemmatiser;

	@Autowired
	private NameAndTitleExtractor nameAndTitleExtractor;

	@Autowired
	private DoctorService doctorService;

	private int counter = 0;

	private static BufferedReader reader;

	static {
		reader = new BufferedReader(new InputStreamReader(System.in));
	}

	public String doctorIntent(String userQuery) {

		String name = nameAndTitleExtractor.getName(userQuery);

		if (!name.equalsIgnoreCase("Name not found"))
			return docService.getDoctor(name);

		userQuery = lemmatiser.getLemmatizedText(userQuery);
		String specialization = nameAndTitleExtractor.getTitle(userQuery);

		if (!specialization.equalsIgnoreCase("Title not found")) {
			return docService.getDoctorsBySpecialization(specialization);
		}

		return docService.getDoctors().toString();
	}

	public String getDoctorDetails(String input) throws IOException {
		String name = nameAndTitleExtractor.getName(input);
		int flag;

		if (name.equalsIgnoreCase("Name not found")) {
			counter = 0;
			do {
				System.out.println("Please provide name of the doctor");
				name = reader.readLine();
				name = nameAndTitleExtractor.getName(name);
				counter++;

			} while (name.equalsIgnoreCase("Name not found") && counter <= 1);

			if (!name.equalsIgnoreCase("Name not found")) {
				flag = doctorService.checkDoctorExists(name);
				if (flag != 0)
					return name;
			} else {
				return getDoctorDetailsFromServerSide();
			}
		}

		return name;

	}

	public String getDoctorDetailsFromServerSide() throws IOException {
		counter = 0;
		String name;
		int flag;

		do {
			System.out.println("Bot :Please enter any of the below doctor names");
			doctorService.getDoctors().stream().forEach(System.out::println);
			System.out.print("Bot :");
			name = reader.readLine();
			flag = doctorService.checkDoctorExists(name);
			counter++;
		} while (flag == 0 && counter <= 1);
		if (flag == 0)
			return null;

		return name;
	}
}
