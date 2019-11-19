package com.pramati.bot.dao;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pramati.bot.dto.DoctorInfoDTO;

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

	@Transactional
	public int deleteDoctor(String name) {
		String query = "delete from doctor where doc_name=:name";
		return entityManager.createNativeQuery(query).setParameter("name", name).executeUpdate();
	}

	public List<DoctorInfoDTO> getDoctors() {
		return (List<DoctorInfoDTO>) entityManager.createNamedQuery("getDoctors").getResultList();
	}

	public List<DoctorInfoDTO> getDoctorsBySpecialization(String specialization) {

		return (List<DoctorInfoDTO>) entityManager.createNamedQuery("getDoctorsBySpecialization")
				.setParameter("specialization", specialization).getResultList();
	}

	public DoctorInfoDTO getDoctor(String name) {
		try {
			return (DoctorInfoDTO) entityManager.createNamedQuery("DoctorInfoDTO").setParameter("name", name)
					.getSingleResult();
		} catch (Exception e) {
			throw new RuntimeException();
		}

	}

	public int checkDoctorExists(String name, String specialization) {
		String query = "select count(doc_id) from doctor where doc_name=:name and specialization=:specialization";
		BigInteger count = (BigInteger) entityManager.createNativeQuery(query).setParameter("name", name)
				.setParameter("specialization", specialization).getSingleResult();
		return count.intValue();
	}

	public int getDoctorId(String name) {
		String docId_query = "select doc_id from doctor where doc_name=:name";
		return (int) entityManager.createNativeQuery(docId_query).setParameter("name", name).getSingleResult();
	}

	public int checkDoctorExists(String name) {
		String query = "select count(doc_id) from doctor where doc_name=:name";
		BigInteger count = (BigInteger) entityManager.createNativeQuery(query).setParameter("name", name)
				.getSingleResult();
		return count.intValue();
	}

}
