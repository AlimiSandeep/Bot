package com.pramati.bot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pramati.bot.dao.PatientDao;
import com.pramati.bot.entities.Patient;

@Service
public class PatientService {

	@Autowired
	PatientDao dao;

	public List<Object[]> getPatients() {
		return dao.getPatients();
	}

	public List<Object[]> getPatientAppointments(String name) {
		return dao.getPatientAppointments(name);
	}

	public String newPatient(String name, int contact, String city) {
		String output = null;
		try {
			int updatedCount = dao.newPatient(name, contact, city);
			if (updatedCount == 1)
				output = "Succesfully inserted";
		} catch (Exception e) {
			output = "Patient already exists with given name...";
		}
		return output;
	}

	public String deletePatient(String name) {
		int flag =dao.deletePatient(name);
		if (flag == 1)
			return "Succesfully deleted";
		return "Deletion failed.....As no patient exists with the name provided";
	}

	public List<String> getAvailableSlotsForPatient(String date, int pId) {
		return dao.getAvailableSlotsForPatient(date, pId);
	}
}
