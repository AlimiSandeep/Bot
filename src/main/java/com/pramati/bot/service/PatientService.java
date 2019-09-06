package com.pramati.bot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.pramati.bot.dao.PatientDao;
import com.pramati.bot.dto.PatientInfoDTO;


@Service
public class PatientService {

	@Autowired
	private PatientDao dao;


	public List<PatientInfoDTO> getPatients() {
		return dao.getPatients();
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
		int flag = dao.deletePatient(name);
		if (flag == 1)
			return "Succesfully deleted";
		return "Deletion failed.....As no patient exists with the name provided";
	}

	public String getPatientInfo(String name) {
		PatientInfoDTO patientDTO = dao.getPatientInfo(name);
		if (patientDTO == null)
			return "No patient exists with given name";
		return patientDTO.toString();
	}

	public int getPatientId(String name) {
		return dao.getPatientId(name);

	}
}
