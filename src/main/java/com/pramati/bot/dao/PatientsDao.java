package com.pramati.bot.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PatientsDao {

	@PersistenceContext
	private EntityManager entityManager;

	public List<Object[]> getPatients() {
		String query = "select name,contact,city from Patient";
		return (List<Object[]>) entityManager.createQuery(query).getResultList();
	}

	@Transactional
	public int newPatient(String name, int contact, String city) {

		try {
			String query = "insert into patients(name,contact,city) values(:name,:contact,:city)";
			return entityManager.createNativeQuery(query).setParameter("name", name).setParameter("contact", contact)
					.setParameter("city", city).executeUpdate();
		} catch (Exception e) {
			return 0;
		}

	}

	public List<Object[]> getPatientAppointments(String name) {
		String pid_query = "select pid from Patient where name=:name";
		int pid = (int) entityManager.createQuery(pid_query).setParameter("name", name).getSingleResult();
		String query = "select p.name,d.doc_name,a.appointment_date,s.slot_time from appointments a,patients p,doctors d,slots s where a.pid=p.pid "
				+ "and a.doc_id=d.doc_id and a.slot_id=s.slot_id and a.pid=:pid";

		return entityManager.createNativeQuery(query).setParameter("pid", pid).getResultList();

	}

	@Transactional
	public int deletePatient(String name) {
		String query = "delete from Patient where name=:name";
		return entityManager.createQuery(query).setParameter("name", name).executeUpdate();
	}

}
