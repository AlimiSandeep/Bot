package com.pramati.bot.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class DoctorDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public int newDoctor(String name, String specialization) {
		String query = "insert into doctor(doc_name,specialization) values(:name,:specialization)";
		return entityManager.createNativeQuery(query).setParameter("name", name)
				.setParameter("specialization", specialization).executeUpdate();
	}

	public List<Object[]> getDoctors() {
		String query = "select doc_name,specialization from doctor";
		return (List<Object[]>) entityManager.createNativeQuery(query).getResultList();
	}

	public List<String> getAvailableSlots(String date, String docName) {
		String query = "select distinct s.slot_time from slot s INNER JOIN appointment b on b.slot_id!=s.slot_id and s.slot_id not in("
				+ "select slot_id from appointment where appointment_date=:date and doc_id=(select doc_id from doctor where doc_name=:docName));";
		return entityManager.createNativeQuery(query).setParameter("date", date).setParameter("docName", docName)
				.getResultList();

	}

	@Transactional
	public int deleteDoctor(String name) {
		String query = "delete from doctor where doc_name=:name";
		return entityManager.createNativeQuery(query).setParameter("name", name).executeUpdate();
	}

}
