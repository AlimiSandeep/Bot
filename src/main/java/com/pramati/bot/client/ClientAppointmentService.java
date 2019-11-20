package com.pramati.bot.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pramati.bot.service.AppointmentService;
import com.pramati.bot.util.DateAndTimeExtractor;
import com.pramati.bot.util.NameAndTitleExtractor;

@Service
public class ClientAppointmentService {

	private static BufferedReader reader;

	@Autowired
	private NameAndTitleExtractor nameAndTitleExtractor;

	@Autowired
	private DateAndTimeExtractor dateAndTimeExtractor;

	@Autowired
	private AppointmentService appointmentService;

	static {
		reader = new BufferedReader(new InputStreamReader(System.in));
	}

	public String appointmentIntent(String userQuery) throws IOException, ParseException {
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

	public String getDoctorDetails(String input) throws IOException {
		String name = nameAndTitleExtractor.getName(input);
		if (name.equalsIgnoreCase("Name not found")) {
			do {
				System.out.println("Please provide name of the doctor");
				name = reader.readLine();
				name = nameAndTitleExtractor.getName(name);

			} while (name.equalsIgnoreCase("Name not found"));
		}

		return name;

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

			} while (patientName.equalsIgnoreCase("Name not found"));
		}

		return patientName;

	}

}
