package com.pramati.bot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pramati.bot.dao.AppointmentDao;
import com.pramati.bot.dto.AppointmentInfoDTO;

@Service
public class AppointmentService {

	@Autowired
<<<<<<< HEAD
	AppointmentDao dao;

	public String createAppointment(int doc_id, String slot_time, String appointment_date, int pid) {
		return dao.createAppointment(doc_id, slot_time, appointment_date, pid);
	}

	public String getAppointments(String appointment_date) {
		return dao.getAppointments(appointment_date);
=======
	private AppointmentDao dao;

	@Autowired
	private SlotService slotService;

	public String createAppointment(int docId, String slotTime, String appointmentDate, int pId) {
		int updatedCount = dao.createAppointment(docId, slotTime, appointmentDate, pId);
		if (updatedCount == 1)
			return "Appointment created successfully";

		return "No slots are available at this time\nAvailable slots are on " + appointmentDate + " are ::\n"
				+ slotService.getAvailableSlotsForPatient(appointmentDate, pId);
	}

	public String getAppointments(String appointmentDate) {
		int count = checkAppointmentsExistsOnDate(appointmentDate);
		if (count == 0)
			return "No appointments exists on the date " + appointmentDate;

		return dao.getAppointments(appointmentDate).toString();
	}

	private int checkAppointmentsExistsOnDate(String appointmentDate) {
		return dao.checkAppointmentsExistsOnDate(appointmentDate);

	}

	public String getAppointment(int appointmentId) {
		String output = null;
		try {
			AppointmentInfoDTO appointmentDTO = dao.getAppointment(appointmentId);
			output = appointmentDTO.toString();
		} catch (Exception e) {
			output = "No appointment exists with given id";
		}
		return output;

	}

	public String getPatientAppointments(String name) {
		List<AppointmentInfoDTO> list = dao.getPatientAppointments(name);
		if (list.isEmpty())
			return "No appointments exists for given name";
		return list.toString();
	}

	public String deleteAppointment(int appointmentId) {
		int flag = dao.deleteAppointment(appointmentId);
		if (flag == 0)
			return "No Appointment exists with given ID";
		return "Appointment deleted successfully";

>>>>>>> 3390e22... used DTO's for projections
	}
}
