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
import com.pramati.bot.util.DateUtil;
import com.pramati.bot.util.NameAndTitleExtractor;

@Service
public class ClientAppointmentService {

	@Autowired
	private NameAndTitleExtractor nameAndTitleExtractor;

	@Autowired
	private DateUtil dateUtil;

	@Autowired
	private DateAndTimeExtractor dateAndTimeExtractor;

	@Autowired
	private AppointmentService appointmentService;

	private static BufferedReader reader;

	static {
		reader = new BufferedReader(new InputStreamReader(System.in));
	}

	private int counter = 0;

	public String appointmentIntent(String userQuery) throws IOException, ParseException {
		String docName = getDoctorDetails(userQuery);
		if (docName.equalsIgnoreCase("Sorry,I didn't get you"))
			return docName;

		String date = dateAndTimeExtractor.getDate(userQuery);
		String time = dateAndTimeExtractor.getTime(userQuery);

		if (date.equalsIgnoreCase("Date not found")) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			date = dateFormat.format(new Date());
		} else {
			boolean flag = dateUtil.dateComparer(date);
			if (!flag) {
				counter = 0;
				do {
					System.out.println("Please provide valid date");
					date = dateAndTimeExtractor.getDate(reader.readLine());

					flag = dateUtil.dateComparer(date);
					counter++;
				} while (!flag && counter <= 2);
			}
			if (date.equalsIgnoreCase("Date not found"))
				return "Sorry,I didn't get you";
		}

		if (time.equalsIgnoreCase("Time not found")) {

			counter = 0;
			do {
				System.out.println("Please provide time for the appointment");
				time = reader.readLine();
				time = dateAndTimeExtractor.getTime(time);
				counter++;
			} while (time.equalsIgnoreCase("Time not found") && counter <= 2);

			if (time.equalsIgnoreCase("Time not found"))
				return "Sorry,I didn't get you";
		}

		return appointmentService.createAppointment(docName, time, date, getPatientDetails());
	}

	public String getDoctorDetails(String input) throws IOException {
		String name = nameAndTitleExtractor.getName(input);
		counter = 0;
		if (name.equalsIgnoreCase("Name not found")) {
			do {
				System.out.println("Please provide name of the doctor");
				name = reader.readLine();
				name = nameAndTitleExtractor.getName(name);
				counter++;

			} while (name.equalsIgnoreCase("Name not found") && counter <= 2);

		}
		if (name.equalsIgnoreCase("Name not found"))
			return "Sorry,I didn't get you";

		return name;

	}

	public String getPatientDetails() throws IOException {
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

			} while (patientName.equalsIgnoreCase("Name not found") && counter <= 2);
			if (patientName.equalsIgnoreCase("Name not found"))
				return "Sorry,I didn't get you";
		}

		return patientName;

	}

}
