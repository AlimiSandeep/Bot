package com.pramati.bot.dao;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository
public class SlotDao {
	@PersistenceContext
	private EntityManager entityManager;

	public int getSlotId(String slotTime) {
		String slot_query = "select slot_id from slot where slot_time=:slot_time";

		return (int) entityManager.createNativeQuery(slot_query).setParameter("slot_time", slotTime).getSingleResult();

	}

	public List<String> getAvailableSlots(String date, String docName) {
		String query = "select distinct s.slot_time from slot s INNER JOIN appointment b on b.slot_id!=s.slot_id and s.slot_id not in("
				+ "select slot_id from appointment where appointment_date=:date and doc_id=(select doc_id from doctor where doc_name=:docName));";
		return entityManager.createNativeQuery(query).setParameter("date", date).setParameter("docName", docName)
				.getResultList();

	}

	public int checkSlotAvailability(String date, String docName) {
		String query = "select count(slot_id) from appointment where appointment_date=:date and doc_id=(select doc_id from doctor where doc_name=:docName);";
		BigInteger count = (BigInteger) entityManager.createNativeQuery(query).setParameter("date", date)
				.setParameter("docName", docName).getSingleResult();
		return count.intValue();
	}

	public List<String> getSlots() {
		String query = "select slot_time from slot";
		return entityManager.createNativeQuery(query).getResultList();
	}
}
//select distinct s.slot_time from slot s INNER JOIN appointment b on b.slot_id!=s.slot_id and s.slot_id not in(select slot_id from appointment where appointment_date='2019-10-01' and doc_id=(select doc_id from doctor where doc_name='SHIVANI'));
