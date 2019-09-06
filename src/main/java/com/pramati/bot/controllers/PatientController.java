package com.pramati.bot.controllers;

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
<<<<<<< HEAD
	public String getPatients() {
		return patientService.getPatients();
	}
	
	
	
	@RequestMapping(value = "/patients/{name}", method = RequestMethod.GET)
	public String getPatientAppointments(@PathVariable String name) {
		return patientService.getPatientAppointments(name);
	}
	
=======
	public List<PatientInfoDTO> getPatients() {
		return patientService.getPatients();
	}

>>>>>>> 3390e22... used DTO's for projections
	@RequestMapping(value = "/patient", method = RequestMethod.PUT)
	public String newDoctor(@RequestParam String name, @RequestParam int contact,@RequestParam String city) {
		int updatedCount = patientService.newPatient(name, contact,city);
		if (updatedCount == 1)
			return "Succesfully inserted";
		return "Patient already exists with given name...";

	}
	
	@RequestMapping(value = "/patient", method = RequestMethod.DELETE)
	public String deletePatient(@RequestParam String name) {
		int flag = patientService.deletePatient(name);
		if (flag == 1)
			return "Succesfully deleted";
		return "Deletion failed.....As no patient exists with the name provided";

	}
	
	

	@RequestMapping(value = "/patient/{name}", method = RequestMethod.GET)
	public String getPatientInfo(@PathVariable String name) {
		return patientService.getPatientInfo(name);
	}

}
