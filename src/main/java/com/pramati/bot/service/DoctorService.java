package com.pramati.bot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pramati.bot.dao.DoctorDao;

@Service
public class DoctorService {

	@Autowired
	private DoctorDao dao;

	public String newDoctor(String name, String specialization) {
		String output = null;
		try {
			int updatedCount = dao.newDoctor(name, specialization);
			if (updatedCount == 1)
				output = "Succesfully inserted";
		} catch (Exception e) {
			output = "Doctor already exist with given name....!!";
		}
		return output;

	}

	public List<Object[]> getDoctors() {
		return dao.getDoctors();
	}

	public List<String> getAvailableSlots(String date, String docName) {
		return dao.getAvailableSlots(date, docName);
	}

	public String deleteDoctor(String name) {
		int flag = dao.deleteDoctor(name);
		if (flag == 1)
			return "Succesfully deleted";
		return "Deletion failed.....As no doctor exists with the name provided";

	}

}
