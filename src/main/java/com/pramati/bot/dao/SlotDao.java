package com.pramati.bot.dao;

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
}
