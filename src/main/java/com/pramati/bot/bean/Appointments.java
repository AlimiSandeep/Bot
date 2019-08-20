package com.pramati.bot.bean;

public class Appointments {

	private int book_id;
	private Doctors doctors;
	private Slots slots;
	private String status;
	private String date;
	private Patients patients;

	public Patients getPatients() {
		return patients;
	}

	public void setPatients(Patients patients) {
		this.patients = patients;
	}

	public int getBook_id() {
		return book_id;
	}

	public void setBook_id(int book_id) {
		this.book_id = book_id;
	}

	public Doctors getDoctors() {
		return doctors;
	}

	public void setDoctors(Doctors doctors) {
		this.doctors = doctors;
	}

	
	public Slots getSlots() {
		return slots;
	}

	public void setSlots(Slots slots) {
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
