package com.pramati.bot.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pramati.bot.models.Slot;

@Repository
public class DoctorsDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public int newDoctor(String name, String specialization) {
		String query = "insert into doctors(doc_name,specialization) values(:name,:specialization)";

		try {
			return entityManager.createNativeQuery(query).setParameter("name", name)
					.setParameter("specialization", specialization).executeUpdate();
		} catch (Exception e) {
			return 0;
		}

	}

	public List<Object[]> getDoctors() {
		String query = "select doc_name,specialization from doctors";
		return (List<Object[]>) entityManager.createNativeQuery(query).getResultList();
	}

	public List<String> getAvailableSlots(String date, int doc_id) {
		String query = "select distinct s.slot_time from slots s INNER JOIN appointments b on b.slot_id!=s.slot_id and s.slot_id not in(select slot_id from appointments where appointment_date=:date and doc_id=:doc_id)";
		return entityManager.createNativeQuery(query).setParameter("date", date).setParameter("doc_id", doc_id)
				.getResultList();

	}

	public List<String> getAvailableSlotsForPatient(String date, int pid) {
		String query = "select distinct s.slot_time from slots s INNER JOIN appointments b on b.slot_id!=s.slot_id and s.slot_id not in"
				+ "(select slot_id from appointments where appointment_date=:date and pid=:pid)\n" + "";
		return entityManager.createNativeQuery(query).setParameter("date", date).setParameter("pid", pid)
				.getResultList();

	}

	@Transactional
	public int deleteDoctor(String name) {
		String query = "delete from doctors where doc_name=:name";
		return entityManager.createNativeQuery(query).setParameter("name", name).executeUpdate();
	}

}
