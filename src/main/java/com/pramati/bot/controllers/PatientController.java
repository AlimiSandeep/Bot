package com.pramati.bot.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pramati.bot.dto.PatientInfoDTO;
import com.pramati.bot.service.PatientService;

@RestController
public class PatientController {

	@Autowired
	private PatientService patientService;

	@RequestMapping(value = "/patients", method = RequestMethod.GET)

	public String getPatients() {
		return patientService.getPatients();
	}
	
	
	
	@RequestMapping(value = "/patients/{name}", method = RequestMethod.GET)
	public String getPatientAppointments(@PathVariable String name) {
		return patientService.getPatientAppointments(name);
	}
	

	@RequestMapping(value = "/patient", method = RequestMethod.PUT)
	public String newPatient(@RequestParam String name, @RequestParam int contact, @RequestParam String city) {

		return patientService.newPatient(name, contact, city);
	}

	@RequestMapping(value = "/patient", method = RequestMethod.DELETE)
	public String deletePatient(@RequestParam String name) {

		return patientService.deletePatient(name);

	}

	@RequestMapping(value = "/patient/{name}", method = RequestMethod.GET)
	public String getPatientInfo(@PathVariable String name) {
		return patientService.getPatientInfo(name);
	}

	@RequestMapping(value = "/patient/{name}", method = RequestMethod.GET)
	public String getPatientInfo(@PathVariable String name) {
		return patientService.getPatientInfo(name);
	}

}
