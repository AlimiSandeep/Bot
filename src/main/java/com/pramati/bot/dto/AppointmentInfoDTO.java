package com.pramati.bot.dto;

public class AppointmentInfoDTO {

	private String patientName;
	private String docName;
	private String appointmentDate;
	private String appointmentTime;

	public AppointmentInfoDTO(String patientName, String docName, String appointmentDate, String appointmentTime) {
		super();
		this.patientName = patientName;
		this.docName = docName;
		this.appointmentDate = appointmentDate;
		this.appointmentTime = appointmentTime;
	}

	public String getPatientName() {
		return patientName;
	}

	public String getDocName() {
		return docName;
	}

	public String getAppointmentDate() {
		return appointmentDate;
	}

	public String getAppointmentTime() {
		return appointmentTime;
	}

	@Override
	public String toString() {
		return patientName + " " + docName + " " + appointmentDate + " " + appointmentTime;
	}

}
