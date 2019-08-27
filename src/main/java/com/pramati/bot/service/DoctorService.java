package com.pramati.bot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pramati.bot.dao.DoctorsDao;

@Service
public class DoctorService {

	@Autowired
	DoctorsDao dao;

	public int newDoctor(String name, String specialization) {
		return dao.newDoctor(name, specialization);
	}

	public String getDoctors() {
		return dao.getDoctors();
	}

	public String getAvailableSlots(String date, int doc_id) {
		return dao.getAvailableSlots(date, doc_id);
	}

	public String getAvailableSlotsForPatient(String date, int pid) {
		return dao.getAvailableSlotsForPatient(date, pid);
	}

	public int deleteDoctor(String name) {
		return dao.deleteDoctor(name);
	}

}
