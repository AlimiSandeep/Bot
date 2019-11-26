package com.pramati.bot.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pramati.bot.dao.SlotDao;

@Service
public class SlotService {

	@Autowired
	private SlotDao dao;

	@Autowired
	private DoctorService doctorService;

	public int getSlotId(String slotTime) {
		try {
			return dao.getSlotId(slotTime);
		} catch (Exception e) {
			return 0;
		}

	}

	public String getAvailableSlots(String date, String docName) throws ParseException {
		int docCount = doctorService.checkDoctorExists(docName);
		if (docCount == 0)
			return "No doctor exists with given name";

		return dao.getAvailableSlots(date, docName).toString();

	}

	public int checkSlotAvailability(String date, String docName) {
		return dao.checkSlotAvailability(date, docName);
	}

	public List<String> getSlots() {
		return dao.getSlots();
	}

}
