package com.pramati.bot.dao;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pramati.bot.dto.AppointmentInfoDTO;
import com.pramati.bot.service.PatientService;
import com.pramati.bot.service.SlotService;

@Repository
public class AppointmentDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private SlotService slotService;

	@Autowired
	private PatientService patientService;

	@Transactional
	public int createAppointment(int docId, String slotTime, String appointmentDate, int pId) {
		int flag = checkAppointmentExists(slotTime, appointmentDate, pId);
		if (flag < 1) {
			int slotId = slotService.getSlotId(slotTime);
			String query = "insert into appointment(doc_id,slot_id,status,appointment_date,pid) values(:doc_id,:slot_id,:status,:appointment_date,:pid)";
			return entityManager.createNativeQuery(query).setParameter("doc_id", docId).setParameter("slot_id", slotId)
					.setParameter("status", "Y").setParameter("appointment_date", appointmentDate)
					.setParameter("pid", pId).executeUpdate();
		}
		return 0;
	}

	public int checkAppointmentExists(String slotTime, String appointmentDate, int pId) {
		String check_query = "select count(b.status) from appointment b,slot s where b.slot_id=s.slot_id and s.slot_time=:slot_time and "
				+ "b.appointment_date=:appointment_date and b.pid=:pid";

		BigInteger count = (BigInteger) entityManager.createNativeQuery(check_query).setParameter("slot_time", slotTime)
				.setParameter("appointment_date", appointmentDate).setParameter("pid", pId).getSingleResult();

		return count.intValue();

	}

//	list of appointments on a date
//	i.e Getting all appointments details(patient_name,appointment_date,doc_name,time_of_appointment) for a date
	public List<AppointmentInfoDTO> getAppointments(String appointmentDate) {

		return entityManager.createNamedQuery("getAppointmentsByDate").setParameter("date", appointmentDate)
				.getResultList();
	}

	public AppointmentInfoDTO getAppointment(int appointmentId) {
		try {
			return (AppointmentInfoDTO) entityManager.createNamedQuery("getAppointmentByID")
					.setParameter("appointmentId", appointmentId).getSingleResult();
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	public List<AppointmentInfoDTO> getPatientAppointments(String name) {

		int pid = patientService.getPatientId(name);

		return entityManager.createNamedQuery("listAppointmentsByName").setParameter("pid", pid).getResultList();

	}

	public int checkAppointmentsExistsOnDate(String appointmentDate) {
		String query = "select count(appointment_id) from appointment where appointment_date=:appointmentDate";
		BigInteger count = (BigInteger) entityManager.createNativeQuery(query)
				.setParameter("appointmentDate", appointmentDate).getSingleResult();
		return count.intValue();
	}

	@Transactional
	public int deleteAppointment(int appointmentId) {
		String query = "delete from appointment where appointment_id=:id";
		return entityManager.createNativeQuery(query).setParameter("id", appointmentId).executeUpdate();
	}
}
