package com.pramati.bot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pramati.bot.service.IPatientService;

@RestController
public class PatientController {

	@Autowired
	private IPatientService patientService;

	@RequestMapping(value = "/patients", method = RequestMethod.GET)
	public String getPatients() {
		return patientService.getPatients();
	}
	
	
	
	@RequestMapping(value = "/get-details-by-name", method = RequestMethod.GET)
	public String getPatientAppointments(@RequestParam String name) {
		return patientService.getPatientAppointments(name);
	}
	
	@RequestMapping(value = "/new-patient", method = RequestMethod.POST)
	public String newDoctor(@RequestParam String name, @RequestParam int contact) {
		int updatedCount = patientService.newPatient(name, contact);
		if (updatedCount == 1)
			return "Succesfully inserted";
		return "Record insertion failed";

	}
	
	@RequestMapping(value = "/delete-patient", method = RequestMethod.DELETE)
	public String deletePatient(@RequestParam String name) {
		int flag = patientService.deletePatient(name);
		if (flag == 1)
			return "Succesfully deleted";
		return "Deletion failed.....As no patient exists with the name provided";

	}
	
	

}
