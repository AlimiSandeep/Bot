package com.pramati.bot.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PatientDao {

	@PersistenceContext
	private EntityManager entityManager;

	public List<Object[]> getPatients() {
		String query = "select name,contact,city from Patient";
		return (List<Object[]>) entityManager.createQuery(query).getResultList();
	}

	@Transactional
	public int newPatient(String name, int contact, String city) {
		String query = "insert into patient(name,contact,city) values(:name,:contact,:city)";
		return entityManager.createNativeQuery(query).setParameter("name", name).setParameter("contact", contact)
				.setParameter("city", city).executeUpdate();

	}

	public List<Object[]> getPatientAppointments(String name) {
		String pid_query = "select pId from Patient where name=:name";
		int pid = (int) entityManager.createQuery(pid_query).setParameter("name", name).getSingleResult();
		String query = "select p.name,d.doc_name,a.appointment_date,s.slot_time from appointment a,patient p,doctor d,slot s where a.pid=p.pid "
				+ "and a.doc_id=d.doc_id and a.slot_id=s.slot_id and a.pid=:pid";

		return entityManager.createNativeQuery(query).setParameter("pid", pid).getResultList();

	}

	public List<String> getAvailableSlotsForPatient(String date, int pId) {
		String query = "select distinct s.slot_time from slot s INNER JOIN appointment b on b.slot_id!=s.slot_id and s.slot_id not in"
				+ "(select slot_id from appointment where appointment_date=:date and pid=:pId)";
		return entityManager.createNativeQuery(query).setParameter("date", date).setParameter("pId", pId)
				.getResultList();

	}

	@Transactional
	public int deletePatient(String name) {
		String query = "delete from Patient where name=:name";
		return entityManager.createQuery(query).setParameter("name", name).executeUpdate();
	}

}
