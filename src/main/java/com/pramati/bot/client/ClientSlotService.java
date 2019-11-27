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

	private static BufferedReader reader;

	static {
		reader = new BufferedReader(new InputStreamReader(System.in));
	}

	@Autowired
	private SlotService slotService;

	@Autowired
	private DateUtil dateService;

	@Autowired
	private ClientAppointmentService clientAppointmentService;

	@Autowired
	private DateAndTimeExtractor dateAndTimeExtractor;

	public String slotIntent(String userQuery) throws IOException, ParseException {
		String date = dateAndTimeExtractor.getDate(userQuery);

		if (date.equalsIgnoreCase("Date not found")) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			date = dateFormat.format(new Date());
		}

		String docName = clientAppointmentService.getDoctorDetails(userQuery);
		if (docName.equalsIgnoreCase("Sorry,I didn't get you"))
			return docName;

		String newDate = null;

		int count = slotService.checkSlotAvailability(date, docName);
		if (count != 6)
			System.out.println(slotService.getAvailableSlots(date, docName));
		else {
			do {
				newDate = dateService.addDays(date);
				count = slotService.checkSlotAvailability(newDate, docName);
			} while (count == 6);
			date = newDate;
			System.out.println("No slots are available on :: " + date + "\nNext available slots are on:: " + newDate
					+ "\n" + slotService.getAvailableSlots(newDate, docName));
		}

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
}
