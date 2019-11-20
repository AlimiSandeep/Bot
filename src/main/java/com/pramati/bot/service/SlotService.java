package com.pramati.bot.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pramati.bot.dao.SlotDao;
import com.pramati.bot.util.DateService;

@Service
public class SlotService {

	@Autowired
	private SlotDao dao;

	@Autowired
	private DoctorService doctorService;

	@Autowired
	private DateService dateService;

	public int getSlotId(String slotTime) {
		try {
			return dao.getSlotId(slotTime);
		} catch (Exception e) {
			return 0;
		}

	}

//	public List<String> getAvailableSlotsForPatient(String date, int pId) {
//		return dao.getAvailableSlotsForPatient(date, pId);
//	}

	public String getAvailableSlots(String date, String docName) throws ParseException {
		int docCount = doctorService.checkDoctorExists(docName);
		if (docCount == 0)
			return "No doctor exists with given name";
		int count = dao.checkSlotAvailability(date, docName);
		String newDate;

		if (count != 6)
			return "Available slots are ::\n" + dao.getAvailableSlots(date, docName);
		else {
			do {
				newDate = dateService.addDays(date);
				count = dao.checkSlotAvailability(newDate, docName);
			} while (count == 6);
		}
		return "No slots are available on :: " + date + "\nNext available slots are on:: " + newDate + "\n"
				+ dao.getAvailableSlots(newDate, docName);

	}

	public List<String> getSlots() {
		return dao.getSlots();
	}

}
