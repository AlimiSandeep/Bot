package com.pramati.bot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

<<<<<<< HEAD
import com.pramati.bot.dao.DoctorsDao;
=======
import com.pramati.bot.dao.DoctorDao;
import com.pramati.bot.dto.DoctorInfoDTO;
>>>>>>> 3390e22... used DTO's for projections

@Service
public class DoctorService {

	@Autowired
	DoctorsDao dao;

	public int newDoctor(String name, String specialization) {
		return dao.newDoctor(name, specialization);
	}

<<<<<<< HEAD
	public String getDoctors() {
		return dao.getDoctors();
	}

	public String getAvailableSlots(String date, int doc_id) {
		return dao.getAvailableSlots(date, doc_id);
=======
	public List<DoctorInfoDTO> getDoctors() {
		return dao.getDoctors();
	}

	public String getAvailableSlots(String date, String docName) {
		int count = checkDoctorExists(docName);
		if (count == 0)
			return "No doctor exists with given name";
		return "AVailable slots are ::\n" + dao.getAvailableSlots(date, docName);
	}

	public int checkDoctorExists(String name) {
		return dao.checkDoctorExists(name);

>>>>>>> 3390e22... used DTO's for projections
	}

	public String getAvailableSlotsForPatient(String date, int pid) {
		return dao.getAvailableSlotsForPatient(date, pid);
	}

	public int deleteDoctor(String name) {
		return dao.deleteDoctor(name);
	}

	public String getDoctor(String name) {
		DoctorInfoDTO doctorDTO = dao.getDoctor(name);
		if (doctorDTO == null)
			return "No doctor exists with given name";
		return doctorDTO.toString();
	}

}
