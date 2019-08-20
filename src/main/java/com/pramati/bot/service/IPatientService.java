package com.pramati.bot.service;

public interface IPatientService {
	public String getPatients();

	public String getPatientAppointments(String name);

	public int newPatient(String name, int contact);

	public int deletePatient(String name);
}
