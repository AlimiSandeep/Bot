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

//	@RequestMapping(value = "/patient", method = RequestMethod.PUT)
//	public String newPatient(@RequestParam String name, @RequestParam int contact) {
//
//		return patientService.newPatient(name, contact);
//	}

	@RequestMapping(value = "/patient", method = RequestMethod.DELETE)
	public String deletePatient(@RequestParam int pid) {

		return patientService.deletePatient(pid);

	}

	@RequestMapping(value = "/patients", method = RequestMethod.GET)
	public List<PatientInfoDTO> getPatients() {
		return patientService.getPatients();
	}

	@RequestMapping(value = "/patient/{name}", method = RequestMethod.GET)
	public String getPatientInfo(@PathVariable String name) {
		return patientService.getPatientInfo(name);
	}

}
