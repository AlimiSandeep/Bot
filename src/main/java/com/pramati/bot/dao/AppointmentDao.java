package com.pramati.bot.dao;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pramati.bot.dto.AppointmentInfoDTO;
import com.pramati.bot.service.SlotService;

@Repository
public class AppointmentDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public int createAppointment(int docId, int slotId, String appointmentDate, int pId) {

		String query = "insert into appointment(doc_id,slot_id,status,appointment_date,pid) values(:doc_id,:slot_id,:status,:appointment_date,:pid)";
		return entityManager.createNativeQuery(query).setParameter("doc_id", docId).setParameter("slot_id", slotId)
				.setParameter("status", "Y").setParameter("appointment_date", appointmentDate).setParameter("pid", pId)
				.executeUpdate();

	}

	public int checkAppointmentExistsAtSameTime(int slotId, String appointmentDate, int pId) {
		String check_query = "select count(a.status) from appointment a where a.slot_id=:slot_id and "
				+ "a.appointment_date=:appointment_date and a.pid=:pid";

		BigInteger count = (BigInteger) entityManager.createNativeQuery(check_query).setParameter("slot_id", slotId)
				.setParameter("appointment_date", appointmentDate).setParameter("pid", pId).getSingleResult();

		return count.intValue();

	}

	public int checkDoctorAvailableAtThatTime(int docId, int slotId, String appointmentDate) {
		String query = "select count(a.status) from appointment a where a.slot_id=:slotId"
				+ " and a.appointment_date=:appointmentDate and a.doc_id=:docId";
		BigInteger count = (BigInteger) entityManager.createNativeQuery(query).setParameter("slotId", slotId)
				.setParameter("appointmentDate", appointmentDate).setParameter("docId", docId).getSingleResult();

		return count.intValue();
	}

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

	public List<AppointmentInfoDTO> getPatientAppointments(int pid) {

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
