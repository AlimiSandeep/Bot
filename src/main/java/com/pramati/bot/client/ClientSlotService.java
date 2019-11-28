package com.pramati.bot.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pramati.bot.service.SlotService;
import com.pramati.bot.util.DateAndTimeExtractor;
import com.pramati.bot.util.DateUtil;

@Service
public class ClientSlotService {

	@Autowired
	private SlotService slotService;

	@Autowired
	private ClientDoctorService clientDoctorService;

	@Autowired
	private DateUtil dateService;

	@Autowired
	private ClientAppointmentService clientAppointmentService;

	@Autowired
	private DateAndTimeExtractor dateAndTimeExtractor;

	@Autowired
	private DateUtil dateUtil;

	private static BufferedReader reader;

	private int counter = 0;

	static {
		reader = new BufferedReader(new InputStreamReader(System.in));
	}

	public String slotIntent(String userQuery) throws IOException, ParseException {
		String date = dateAndTimeExtractor.getDate(userQuery);

		if (date.equalsIgnoreCase("Date not found")) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			date = dateFormat.format(new Date());
		}

		String docName = clientDoctorService.getDoctorDetails(userQuery);
		if (docName == null)
			return "Bot: Sorry,I didn't get you";

		if (dateUtil.dateIsSunday(date)) {
			return "Bot :Sunday is Jollyday....\nBot :No Appointment's are allowed on sundays";

		}

		int count = slotService.checkSlotAvailability(date, docName);
		if (count != 6) {
			System.out.println(slotService.getAvailableSlots(date, docName));

		} else
			date = checkAvailabilityAndGetDate(date, docName);

		System.out.println("Do you want to book appointment with the given details");
		System.out.println("Doctor :" + docName);
		System.out.println("Date :" + date);
		System.out.println("Press [y/n]");
		String choice = reader.readLine();
		if (choice.equalsIgnoreCase("y") || choice.equalsIgnoreCase("yes")) {
			String inputs = date + " " + docName;
			return clientAppointmentService.appointmentIntent(inputs);
		}

		return "Bot: Okay...";

	}

	public String checkAvailabilityAndGetDate(String date, String docName) throws ParseException {
		String newDate = null;

		int count = slotService.checkSlotAvailability(date, docName);

		do {
			newDate = dateService.addDays(date);
			count = slotService.checkSlotAvailability(newDate, docName);
		} while (count == 6);

		System.out.println("No slots are available on :: " + date + "\nNext available slots are on:: " + newDate + "\n"
				+ slotService.getAvailableSlots(newDate, docName));
		date = newDate;
		return date;
	}

	public String fetchTime(String userQuery) throws IOException {
		String time = dateAndTimeExtractor.getTime(userQuery);

		if (time.equalsIgnoreCase("Time not found")) {

			counter = 0;
			do {
				System.out.println("Please provide time for the appointment");
				time = reader.readLine();
				time = dateAndTimeExtractor.getTime(time);
				counter++;
			} while (time.equalsIgnoreCase("Time not found") && counter <= 2);

			if (time.equalsIgnoreCase("Time not found"))
				return null;
		}
		return time;
	}

	public String fetchDate(String userQuery) throws IOException {
		String date = dateAndTimeExtractor.getDate(userQuery);
		boolean flag = false;
		if (date.equalsIgnoreCase("Date not found")) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			date = dateFormat.format(new Date());
		} else {

			try {
				flag = dateUtil.dateComparer(date);
			} catch (ParseException e) {
				return null;
			}
			if (!flag) {
				counter = 0;
				do {
					System.out.println("Please provide valid date");
					date = dateAndTimeExtractor.getDate(reader.readLine());

					try {
						flag = dateUtil.dateComparer(date);
					} catch (ParseException e) {
						return null;
					}
					counter++;
				} while (!flag && counter < 2);
			}
			if (date.equalsIgnoreCase("Date not found") || !flag)
				return null;
		}

		return date;
	}

}
