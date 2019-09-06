package com.pramati.bot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pramati.bot.service.AppointmentService;

@RestController
public class AppointmentController {

	@Autowired
	private AppointmentService appointmentService;

	@RequestMapping(value = "/appointment", method = RequestMethod.POST)
	public String createAppointment(@RequestParam int docId, @RequestParam String slotTime,
			@RequestParam String appointmentDate, @RequestParam int pId) {
  		return appointmentService.createAppointment(docId, slotTime, appointmentDate, pId);
	}

	@RequestMapping(value = "/appointments", method = RequestMethod.GET)
	public String getAppointments(@RequestParam("date") String appointmentDate) {
		return appointmentService.getAppointments(appointmentDate);
	}

	@RequestMapping(value = "/appointment/{id}", method = RequestMethod.GET)
	public String getAppointmentsById(@PathVariable("id") int appointmentId) {
		return appointmentService.getAppointment(appointmentId);
	}

	@RequestMapping(value = "/appointment", method = RequestMethod.GET)
	public String getPatientAppointments(@RequestParam String name) {
		return appointmentService.getPatientAppointments(name);
	}

	@RequestMapping(value = "/appointments", method = RequestMethod.GET)
	public String getAppointments(@RequestParam("date") String appointmentDate) {
		return appointmentService.getAppointments(appointmentDate);
	}

	@RequestMapping(value = "/patient/", method = RequestMethod.GET)
	public String getPatientAppointments(@RequestParam String name) {
		return appointmentService.getPatientAppointments(name);
	}

	@RequestMapping(value = "/appointment", method = RequestMethod.DELETE)
	public String deleteAppointment(@RequestParam("id") int appointmentId) {
		return appointmentService.deleteAppointment(appointmentId);
	}
}
