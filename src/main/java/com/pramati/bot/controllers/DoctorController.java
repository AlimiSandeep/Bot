package com.pramati.bot.controllers;

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
		int updatedCount = docService.newDoctor(name, specialization);
		if (updatedCount == 1)
			return "Succesfully inserted";
		return "Doctor already exist with given name....!!";

	}

	@RequestMapping(value = "/doctors", method = RequestMethod.GET)
<<<<<<< HEAD:src/main/java/com/pramati/bot/controllers/DoctorsController.java
	public String getDoctors() {
		return docService.getDoctors();
	}

	@RequestMapping(value = "/doctor/{doc_id}", method = RequestMethod.GET)
	public String getAvailableSlots(@RequestParam String date, @PathVariable int doc_id) {
		return docService.getAvailableSlots(date, doc_id);
=======
	public List<DoctorInfoDTO> getDoctors() {
		return docService.getDoctors();
	}

	@RequestMapping(value = "/doctor/{name}", method = RequestMethod.GET)
	public String getDoctor(@PathVariable String name) {
		return docService.getDoctor(name);
	}

	@RequestMapping(value = "/doctor/{docName}/date/{date}", method = RequestMethod.GET)
	public String getAvailableSlots(@PathVariable String date, @PathVariable String docName) {
		return docService.getAvailableSlots(date, docName);
>>>>>>> 3390e22... used DTO's for projections:src/main/java/com/pramati/bot/controllers/DoctorController.java

	}

	@RequestMapping(value = "/doctor", method = RequestMethod.DELETE)
	public String deleteDoctor(@RequestParam String name) {
		int flag = docService.deleteDoctor(name);
		if (flag == 1)
			return "Succesfully deleted";
		return "Deletion failed.....As no doctor exists with the name provided";

	}
}
