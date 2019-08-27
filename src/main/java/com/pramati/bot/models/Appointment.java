package com.pramati.bot.models;

public class Appointment {

	private int book_id;
	private Doctor doctors;
	private Slot slots;
	private String status;
	private String date;
	private Patient patients;

	public Patient getPatients() {
		return patients;
	}

	public void setPatients(Patient patients) {
		this.patients = patients;
	}

	public int getBook_id() {
		return book_id;
	}

	public void setBook_id(int book_id) {
		this.book_id = book_id;
	}

	public Doctor getDoctors() {
		return doctors;
	}

	public void setDoctors(Doctor doctors) {
		this.doctors = doctors;
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
