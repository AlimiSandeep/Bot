package com.pramati.bot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pramati.bot.dao.DoctorDao;
import com.pramati.bot.dto.DoctorInfoDTO;

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

	public List<DoctorInfoDTO> getDoctors() {
		return dao.getDoctors();
	}

	public int checkDoctorExists(String name) {
		return dao.checkDoctorExists(name);

	}

	public String deleteDoctor(String name) {
		int flag = dao.deleteDoctor(name);
		if (flag == 1)
			return "Succesfully deleted";
		return "Deletion failed.....As no doctor exists with the name provided";

	}

	public String getDoctor(String name) {
		String output = null;
		try {
			DoctorInfoDTO doctorDTO = dao.getDoctor(name);
			output = doctorDTO.toString();
		} catch (Exception e) {
			output = "No doctor exists with given name";
		}

		return output;

	}

	public int getDoctorId(String name) {
		try {
			return dao.getDoctorId(name);
		} catch (Exception e) {
			return 0;
		}
	}

}
