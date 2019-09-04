package com.pramati.bot.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "slots")
public class Slot {

	@Id
	private int slot_id;
	private String slot_time;

	@OneToOne
	@JoinColumn(name="appointments_slot_fk")
	private Appointment appointment;

	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

	public int getSlot_id() {
		return slot_id;
	}

	public void setSlot_id(int slot_id) {
		this.slot_id = slot_id;
	}

	public String getSlot_time() {
		return slot_time;
	}

	public void setSlot_time(String slot_time) {
		this.slot_time = slot_time;
	}

	@Override
	public String toString() {
		return slot_id + " " + slot_time;
	}

}
