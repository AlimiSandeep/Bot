package com.pramati.bot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pramati.bot.service.IAppointmentService;

@RestController
public class AppointmentController {

	@Autowired
	private IAppointmentService appointmentService;

	@RequestMapping(value = "/appointment", method = RequestMethod.POST)
	public String createAppointment(@RequestParam int doc_id, @RequestParam String slot_time,
			@RequestParam String appointment_date, @RequestParam int pid) {
		return appointmentService.createAppointment(doc_id, slot_time, appointment_date, pid);
	}

	@RequestMapping(value = "/appointment/{date}", method = RequestMethod.GET)
	public String getAppointments(@PathVariable("date") String appointment_date) {
		return appointmentService.getAppointments(appointment_date);
	}
}
