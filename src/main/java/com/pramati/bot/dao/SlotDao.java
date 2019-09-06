package com.pramati.bot.dao;

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

		int slotId = (int) entityManager.createNativeQuery(slot_query).setParameter("slot_time", slotTime)
				.getSingleResult();

		return slotId;

	}

	public List<String> getAvailableSlotsForPatient(String date, int pId) {
		String query = "select distinct s.slot_time from slot s INNER JOIN appointment b on b.slot_id!=s.slot_id and s.slot_id not in"
				+ "(select slot_id from appointment where appointment_date=:date and pid=:pId)";
		return entityManager.createNativeQuery(query).setParameter("date", date).setParameter("pId", pId)
				.getResultList();

	}

}
