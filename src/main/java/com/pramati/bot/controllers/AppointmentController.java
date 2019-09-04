package com.pramati.bot.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pramati.bot.service.AppointmentService;
import com.pramati.bot.service.DoctorService;
import com.pramati.bot.service.PatientService;

@RestController
public class AppointmentController {

	@Autowired
	private AppointmentService appointmentService;

	@Autowired
	private DoctorService doctorService;

	@RequestMapping(value = "/appointment", method = RequestMethod.POST)
	public String createAppointment(@RequestParam int doc_id, @RequestParam String slot_time,
			@RequestParam String appointment_date, @RequestParam int pid) {

		try {
			int updatedCount = appointmentService.createAppointment(doc_id, slot_time, appointment_date, pid);
			if (updatedCount == 1)
				return "Appointment created successfully";
		} catch (Exception e) {

		}
		return "No slots are available at this time\nAvailable slots are on " + appointment_date + " are ::\n"
				+ doctorService.getAvailableSlotsForPatient(appointment_date, pid);
		

	}

	@RequestMapping(value = "/appointment/{date}", method = RequestMethod.GET)
	public List<Object[]> getAppointments(@PathVariable("date") String appointment_date) {
		return appointmentService.getAppointments(appointment_date);
	}
}
