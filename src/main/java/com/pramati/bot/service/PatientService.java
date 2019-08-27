package com.pramati.bot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pramati.bot.dao.PatientsDao;

@Service
public class PatientService {

	@Autowired
	PatientsDao dao;

	public String getPatients() {
		return dao.getPatients();
	}

	public String getPatientAppointments(String name) {
		return dao.getPatientAppointments(name);
	}

	public int newPatient(String name, int contact, String city) {
		return dao.newPatient(name, contact, city);
	}

	public int deletePatient(String name) {
		return dao.deletePatient(name);
	}
}
