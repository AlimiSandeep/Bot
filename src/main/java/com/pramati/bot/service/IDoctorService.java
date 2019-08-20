package com.pramati.bot.service;

public interface IDoctorService {
	public int newDoctor(String name, String specialization);

	public String getDoctors();

	public String getAvailableSlots(String date, int doc_id);

	public int deleteDoctor(String name);
}
