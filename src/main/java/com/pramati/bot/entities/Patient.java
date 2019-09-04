package com.pramati.bot.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "patient")
public class Patient {

	@Id
	@Column(name = "pid")
	private int pId;
	private String name;
	private int contact;
	private String city;

	@OneToMany
	@JoinColumn(name = "appointments_pid_fk")
	private List<Appointment> appointments;

	public List<Appointment> getAppointment() {
		return appointments;
	}

	public void setAppointment(List<Appointment> appointments) {
		this.appointments = appointments;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getPid() {
		return pId;
	}

	public void setPid(int pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getContact() {
		return contact;
	}

	public void setContact(int contact) {
		this.contact = contact;
	}

}