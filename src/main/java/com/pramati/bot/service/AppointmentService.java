package com.pramati.bot.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pramati.bot.dao.AppointmentDao;
import com.pramati.bot.dto.AppointmentInfoDTO;
import com.pramati.bot.dto.DoctorInfoDTO;

@Service
public class AppointmentService {

	@Autowired
	private AppointmentDao dao;

	@Autowired
	private SlotService slotService;

	@Autowired
	private PatientService patientService;

	@Autowired
	private DoctorService doctorService;

	public String createAppointment(String docName, String slotTime, String appointmentDate, String patientName)
			throws ParseException {

		int docId = doctorService.getDoctorId(docName);
		if (docId == 0) {
			List<DoctorInfoDTO> listOfDcotors = doctorService.getDoctors();
			return "No doctor exists with given name\nAvailable doctors are ::" + listOfDcotors.toString();
		}

		int slotId = slotService.getSlotId(slotTime);
		if (slotId == 0)
			return "Invalid slot time\nValid slots are ::" + slotService.getSlots();

		int pId = patientService.getPatientId(patientName);
		if (pId == 0)
			return "No patient exists with given name";

		int flag1 = checkAppointmentExistsAtSameTime(slotId, appointmentDate, pId);
		if (flag1 > 0)
			return "You cannot make an appointment at this time\nAs you have made an appointment with doctor " + docName
					+ " at " + slotTime;

		int flag2 = checkDoctorAvailableAtThatTime(docId, slotId, appointmentDate);
		if (flag2 > 0)
			return "No slots are available at this time\n" + slotService.getAvailableSlots(appointmentDate, docName);

		dao.createAppointment(docId, slotId, appointmentDate, pId);
		int appointmentId = getAppointmentId(docId, slotId, appointmentDate, pId);
		return "Appointment created successfully with appointmentId " + appointmentId + "\n\tPlease note it for future refernece";

	}

	public int checkDoctorAvailableAtThatTime(int docId, int slotId, String appointmentDate) {
		return dao.checkDoctorAvailableAtThatTime(docId, slotId, appointmentDate);
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

	public int checkAppointmentExistsAtSameTime(int slotId, String appointmentDate, int pId) {
		return dao.checkAppointmentExistsAtSameTime(slotId, appointmentDate, pId);
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
		int pid = patientService.getPatientId(name);
		if (pid == 0)
			return "No patient exists for given name";

		List<AppointmentInfoDTO> list = dao.getPatientAppointments(pid);

		return list.toString();

	}

	public String deleteAppointment(int appointmentId) {
		int flag = dao.deleteAppointment(appointmentId);
		if (flag == 0)
			return "No Appointment exists with given ID";
		return "Appointment deleted successfully";

	}

	public int getAppointmentId(int docId, int slotId, String appointmentDate, int pId) {
		int appointmentId = dao.getAppointmentId(docId, slotId, appointmentDate, pId);
		return appointmentId;
	}
}
