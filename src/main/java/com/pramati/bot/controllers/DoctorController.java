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

	@RequestMapping(value = "/doctors", method = RequestMethod.GET)
	public String getDoctors() {
		return docService.getDoctors();
	}


	@RequestMapping(value = "/doctor/{name}", method = RequestMethod.GET)
	public String getDoctor(@PathVariable String name) {
		return docService.getDoctor(name);
	}

	@RequestMapping(value = "/doctor/{docName}/date/{date}", method = RequestMethod.GET)
	public String getAvailableSlots(@PathVariable String date, @PathVariable String docName) {
		return docService.getAvailableSlots(date, docName);

	}

	@RequestMapping(value = "/doctor", method = RequestMethod.DELETE)
	public String deleteDoctor(@RequestParam String name) {
		return docService.deleteDoctor(name);

	}
}
