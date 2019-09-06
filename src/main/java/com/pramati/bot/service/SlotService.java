package com.pramati.bot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pramati.bot.dao.SlotDao;

@Service
public class SlotService {

	@Autowired
	private SlotDao dao;

	public int getSlotId(String slotTime) {
		return dao.getSlotId(slotTime);

	}

	public List<String> getAvailableSlotsForPatient(String date, int pId) {
		return dao.getAvailableSlotsForPatient(date, pId);
	}

}
