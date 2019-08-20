package com.pramati.bot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pramati.bot.service.IDoctorService;

@RestController
public class DoctorsController {

	@Autowired
	private IDoctorService docService;

	@RequestMapping(value = "/new-doctor", method = RequestMethod.POST)
	public String newDoctor(@RequestParam String name, @RequestParam String specialization) {
		int updatedCount = docService.newDoctor(name, specialization);
		if (updatedCount == 1)
			return "Succesfully inserted";
		return "Record failed";

	}

	@RequestMapping(value = "/doctors", method = RequestMethod.GET)
	public String getDoctors() {
		return docService.getDoctors();
	}

	@RequestMapping(value = "/available-slots", method = RequestMethod.GET)
	public String getAvailableSlots(@RequestParam String date, @RequestParam int doc_id) {
		return docService.getAvailableSlots(date, doc_id);

	}

	@RequestMapping(value = "/delete-doctor", method = RequestMethod.DELETE)
	public String deleteDoctor(@RequestParam String name) {
		int flag = docService.deleteDoctor(name);
		if (flag == 1)
			return "Succesfully deleted";
		return "Deletion failed.....As no doctor exists with the name provided";

	}
}
