package com.pramati.bot.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pramati.bot.dto.DoctorInfoDTO;
import com.pramati.bot.service.DoctorService;

@RestController
public class DoctorController {

	@Autowired
	private DoctorService docService;

	@RequestMapping(value = "/doctor", method = RequestMethod.PUT)
	public String newDoctor(@RequestParam String name, @RequestParam String specialization) {
		return docService.newDoctor(name, specialization);
	}

	@RequestMapping(value = "/doctor", method = RequestMethod.DELETE)
	public String deleteDoctor(@RequestParam String name) {
		return docService.deleteDoctor(name);

	}

	@RequestMapping(value = "/doctors", method = RequestMethod.GET)
	public List<DoctorInfoDTO> getDoctors() {
		return docService.getDoctors();
	}

	@RequestMapping(value = "/doctors/{specialization}", method = RequestMethod.GET)
	public String getDoctorsBySpecialization(@PathVariable String specialization) {
		return docService.getDoctorsBySpecialization(specialization);
	}

	@RequestMapping(value = "/doctor/{name}", method = RequestMethod.GET)
	public String getDoctor(@PathVariable String name) {
		return docService.getDoctor(name);
	}

}
