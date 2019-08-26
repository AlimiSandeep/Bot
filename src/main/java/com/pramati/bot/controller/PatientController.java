package com.pramati.bot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	
	
	@RequestMapping(value = "/patients/{name}", method = RequestMethod.GET)
	public String getPatientAppointments(@PathVariable String name) {
		return patientService.getPatientAppointments(name);
	}
	
	@RequestMapping(value = "/patient/new", method = RequestMethod.POST)
	public String newDoctor(@RequestParam String name, @RequestParam int contact) {
		int updatedCount = patientService.newPatient(name, contact);
		if (updatedCount == 1)
			return "Succesfully inserted";
		return "Patient already exists with given name...";

	}
	
	@RequestMapping(value = "/patient/remove", method = RequestMethod.DELETE)
	public String deletePatient(@RequestParam String name) {
		int flag = patientService.deletePatient(name);
		if (flag == 1)
			return "Succesfully deleted";
		return "Deletion failed.....As no patient exists with the name provided";

	}
	
	

}
