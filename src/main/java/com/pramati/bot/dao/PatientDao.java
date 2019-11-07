package com.pramati.bot.dao;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pramati.bot.dto.PatientInfoDTO;

@Repository
public class PatientDao {

	@PersistenceContext
	private EntityManager entityManager;

	public List<PatientInfoDTO> getPatients() {
		String query = "select name,contact,city from Patient";
		return (List<PatientInfoDTO>) entityManager.createQuery(query).getResultList();
	}

	@Transactional
	public int newPatient(String name, int contact, String city) {
		String query = "insert into patient(name,contact,city) values(:name,:contact,:city)";
		return entityManager.createNativeQuery(query).setParameter("name", name).setParameter("contact", contact)
				.setParameter("city", city).executeUpdate();

	}

	@Transactional
	public int deletePatient(int pid) {
		String query = "delete from Patient where pid=:pid";
		return entityManager.createQuery(query).setParameter("pid", pid).executeUpdate();
	}

	public PatientInfoDTO getPatientInfo(String name) {
		return (PatientInfoDTO) entityManager.createNamedQuery("PatientInfoDTO").setParameter("name", name)
				.getSingleResult();
	}

	public int getPatientId(String name) {
		String pid_query = "select pId from Patient where name=:name";
		return (int) entityManager.createQuery(pid_query).setParameter("name", name).getSingleResult();

	}

	public int checkPatientExists(String name, int contact, String city) {
		String query = "select count(pid) from patient where name=:name and contact=:contact and city=:city";
		BigInteger count = (BigInteger) entityManager.createNativeQuery(query).setParameter("name", name)
				.setParameter("contact", contact).setParameter("city", city).getSingleResult();
		return count.intValue();
	}
}
