package com.pramati.bot.controllers;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pramati.bot.service.SlotService;

@RestController
public class SlotController {

	@Autowired
	private SlotService slotService;

	@RequestMapping(value = "/slots/{docName}/date/{date}", method = RequestMethod.GET)
	public String getAvailableSlots(@PathVariable String date, @PathVariable String docName) throws ParseException {
		return slotService.getAvailableSlots(date, docName);

	}

}
