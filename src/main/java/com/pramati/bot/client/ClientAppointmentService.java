package com.pramati.bot.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pramati.bot.service.AppointmentService;
import com.pramati.bot.service.SlotService;
import com.pramati.bot.util.DateUtil;

@Service
public class ClientAppointmentService {

	@Autowired
	private ClientDoctorService clientDoctorService;

	@Autowired
	private SlotService slotService;

	@Autowired
	private AppointmentService appointmentService;

	@Autowired
	private ClientRegistration clientRegistration;

	@Autowired
	private ClientSlotService clientSlotService;

	@Autowired
	private DateUtil dateUtil;

	private static BufferedReader reader;

	static {
		reader = new BufferedReader(new InputStreamReader(System.in));
	}

	public String appointmentIntent(String userQuery) throws IOException, ParseException {
		String patientName = null;
		String errorMessage = "Bot: Sorry,I didn't get you";

		String docName = clientDoctorService.getDoctorDetails(userQuery);
		if (docName == null)
			return errorMessage;

		String date = clientSlotService.fetchDate(userQuery);
		if (date == null)
			return errorMessage;
		if (dateUtil.dateIsSunday(date)) {
			return "Bot :Sunday is Jollyday....\nBot :No Appointment's are allowed on sundays";

		}

		String time = clientSlotService.fetchTime(userQuery);
		int count = slotService.checkSlotAvailability(date, docName);
		if (count == 6)
			date = clientSlotService.checkAvailabilityAndGetDate(date, docName);
		if (time == null)
			return errorMessage;

		System.out.println("Are you a registered user [y/n] :");
		String option = reader.readLine();
		if (option.equalsIgnoreCase("y") || option.equalsIgnoreCase("yes"))
			patientName = clientRegistration.getPatientName();
		else
			patientName = clientRegistration.getPatientDetails();

		if (patientName == null)
			return errorMessage;
		

		return "Bot: " + appointmentService.createAppointment(docName, time, date, patientName);
	}

}
