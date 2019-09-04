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

	@RequestMapping(value = "/appointment", method = RequestMethod.POST)
	public String createAppointment(@RequestParam int docId, @RequestParam String slotTime,
			@RequestParam String appointmentDate, @RequestParam int pId) {

		return appointmentService.createAppointment(docId, slotTime, appointmentDate, pId);

	}

	@RequestMapping(value = "/appointment", method = RequestMethod.GET)
	public List<Object[]> getAppointments(@RequestParam("date") String appointmentDate) {
		return appointmentService.getAppointments(appointmentDate);
	}

	@RequestMapping(value = "/appointment/{id}", method = RequestMethod.GET)
	public Object[] getAppointmentsById(@PathVariable("id") int appointmentId) {
		return appointmentService.getAppointment(appointmentId);
	}
}
