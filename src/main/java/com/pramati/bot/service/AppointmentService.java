package com.pramati.bot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pramati.bot.dao.AppointmentDao;

@Service
public class AppointmentService {

	@Autowired
	private AppointmentDao dao;
	
	@Autowired
	private PatientService patientService;
	

	public String createAppointment(int docId, String slotTime, String appointmentDate, int pId) {
		int updatedCount = dao.createAppointment(docId, slotTime, appointmentDate, pId);
		if (updatedCount == 1)
			return "Appointment created successfully";
		
		return "No slots are available at this time\nAvailable slots are on " + appointmentDate + " are ::\n"
		+ patientService.getAvailableSlotsForPatient(appointmentDate, pId);
	}

	public List<Object[]> getAppointments(String appointmentDate) {
		return dao.getAppointments(appointmentDate);
	}

	public Object[] getAppointment(int appointmentId) {
		return dao.getAppointment(appointmentId);
	}
}
