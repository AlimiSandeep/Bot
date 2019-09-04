package com.pramati.bot.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pramati.bot.service.DateService;
import com.pramati.bot.service.EventInsertionService;

@RestController
public class InputController {

	@Autowired
	EventInsertionService eventService;

	@Autowired
	DateService ds;

	@RequestMapping(value = "/event-creation", method = RequestMethod.POST)
	public String getDate(@RequestParam("userInput") String message) throws ParseException, IOException {

		String startDate = ds.getStartDate(message);
		String endDate = ds.getEndDate(startDate);

		boolean flag = false;
		flag = eventService.createEvent(startDate, endDate);
		List<String> availableSlots = eventService.suggestEvents(startDate);

		if (flag)
			return "Successfully created event";
		else
			return "Appointment already exists \nAvailaible slots are ::" + "\n" + availableSlots;
	}

}
