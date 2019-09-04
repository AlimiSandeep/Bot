package com.pramati.bot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pramati.bot.dao.DoctorsDao;
import com.pramati.bot.models.Slot;

@Service
public class DoctorService {

	@Autowired
	DoctorsDao dao;

	public int newDoctor(String name, String specialization) {
		return dao.newDoctor(name, specialization);
	}

	public List<Object[]> getDoctors() {
		return dao.getDoctors();
	}

	public List<String> getAvailableSlots(String date, int doc_id) {
		return dao.getAvailableSlots(date, doc_id);
	}

	public List<String> getAvailableSlotsForPatient(String date, int pid) {
		return dao.getAvailableSlotsForPatient(date, pid);
	}

	public int deleteDoctor(String name) {
		return dao.deleteDoctor(name);
	}

}
