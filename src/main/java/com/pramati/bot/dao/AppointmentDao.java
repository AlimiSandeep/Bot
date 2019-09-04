package com.pramati.bot.dao;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pramati.bot.service.DoctorService;

@Repository
public class AppointmentDao {
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private DoctorService docService;

	@Transactional
	public int createAppointment(int doc_id, String slot_time, String appointment_date, int pid) {

		String check_query = "select count(b.status) from appointments b,slots s where b.slot_id=s.slot_id and s.slot_time=:slot_time and "
				+ "b.appointment_date=:appointment_date and b.pid=:pid";

		BigInteger count = (BigInteger) entityManager.createNativeQuery(check_query)
				.setParameter("slot_time", slot_time).setParameter("appointment_date", appointment_date)
				.setParameter("pid", pid).getSingleResult();

		if (count.intValue() < 1) {
			try {
				String slot_query = "select slot_id from slots where slot_time=:slot_time";

				int slot_id = (int) entityManager.createNativeQuery(slot_query).setParameter("slot_time", slot_time)
						.getSingleResult();

				String query = "insert into appointments(doc_id,slot_id,status,appointment_date,pid) values(:doc_id,:slot_id,:status,:appointment_date,:pid)";

				return entityManager.createNativeQuery(query).setParameter("doc_id", doc_id)
						.setParameter("slot_id", slot_id).setParameter("status", "Y")
						.setParameter("appointment_date", appointment_date).setParameter("pid", pid).executeUpdate();
			} catch (Exception e) {

			}

		}

		return 0;
	}

//	list of appointments on a date
//	i.e Getting all appointments details(patient_name,appointment_date,doc_name,time_of_appointment) for a date
	public List<Object[]> getAppointments(String appointment_date) {
		String query = "select p.name,d.doc_name,s.slot_time from patients p,doctors d,slots s,appointments a where a.pid=p.pid "
				+ "and a.doc_id=d.doc_id and a.slot_id=s.slot_id and a.appointment_date=:date";
		return entityManager.createNativeQuery(query).setParameter("date", appointment_date).getResultList();
	}

}
