package com.pramati.bot.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "doctors")
public class Doctor {
	@Id
	private int doc_id;
	private String doc_name;
	private String specialization;
	
	@ManyToMany
	@JoinColumn(name="appointments_doc_fk")
	private List<Appointment> appointments;

	public List<Appointment> getAppointment() {
		return appointments;
	}

	public void setAppointment(List<Appointment> appointments) {
		this.appointments = appointments;
	}

	public int getDoc_id() {
		return doc_id;
	}

	public void setDoc_id(int doc_id) {
		this.doc_id = doc_id;
	}

	public String getDoc_name() {
		return doc_name;
	}

	public void setDoc_name(String doc_name) {
		this.doc_name = doc_name;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

}
