package com.pramati.bot.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "slot")
public class Slot {

	@Id
	private int slotId;
	private String slotTime;

	@OneToOne
	@JoinColumn(name="appointments_slot_fk")
	private Appointment appointment;

	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

	public int getslotId() {
		return slotId;
	}

	public void setslotId(int slotId) {
		this.slotId = slotId;
	}

	public String getslotTime() {
		return slotTime;
	}

	public void setslotTime(String slotTime) {
		this.slotTime = slotTime;
	}

	@Override
	public String toString() {
		return slotId + " " + slotTime;
	}

}
