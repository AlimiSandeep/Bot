package com.pramati.bot.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "appointments")
public class Appointment {

	@Id
	private int book_id;

	@ManyToMany
	private List<Doctor> doctors;

	@OneToOne
	private Slot slots;

	@ManyToOne
	private Patient patient;

	private String status;

	@Column(name = "appointment_date")
	private String date;

	public Patient getPatient() {
		return patient;
	}

	public void setPatients(Patient patient) {
		this.patient = patient;
	}

	public int getBook_id() {
		return book_id;
	}

	public void setBook_id(int book_id) {
		this.book_id = book_id;
	}

	
	public List<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(List<Doctor> doctors) {
		this.doctors = doctors;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Slot getSlots() {
		return slots;
	}

	public void setSlots(Slot slots) {
		this.slots = slots;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
