package com.pramati.bot.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pramati.bot.service.DoctorService;

@RestController
public class DoctorsController {

	@Autowired
	private DoctorService docService;

	@RequestMapping(value = "/doctor", method = RequestMethod.PUT)
	public String newDoctor(@RequestParam String name, @RequestParam String specialization) {
		String output = null;
		try {
			int updatedCount = docService.newDoctor(name, specialization);
			if (updatedCount == 1)
				return "Succesfully inserted";
		} catch (Exception e) {
			return "Doctor already exist with given name....!!";
		}
		return output;

	}

	@RequestMapping(value = "/doctors", method = RequestMethod.GET)
	public List<Object[]> getDoctors() {
		return docService.getDoctors();
	}

	@RequestMapping(value = "/doctor/{doc_id}", method = RequestMethod.GET)
	public List<String> getAvailableSlots(@RequestParam String date, @PathVariable int doc_id) {
		return docService.getAvailableSlots(date, doc_id);

	}

	@RequestMapping(value = "/doctor", method = RequestMethod.DELETE)
	public String deleteDoctor(@RequestParam String name) {
		int flag = docService.deleteDoctor(name);
		if (flag == 1)
			return "Succesfully deleted";
		return "Deletion failed.....As no doctor exists with the name provided";

	}
}
