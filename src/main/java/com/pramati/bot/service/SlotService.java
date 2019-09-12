package com.pramati.bot.service;

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

//	public List<String> getAvailableSlotsForPatient(String date, int pId) {
//		return dao.getAvailableSlotsForPatient(date, pId);
//	}

	public String getAvailableSlots(String date, String docName) {
		int count = doctorService.checkDoctorExists(docName);
		if (count == 0)
			return "No doctor exists with given name";
		return "AVailable slots are ::\n" + dao.getAvailableSlots(date, docName);

	}

	public List<String> getSlots() {
		return dao.getSlots();
	}

}
