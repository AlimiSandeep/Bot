package com.pramati.bot.dto;

public class DoctorInfoDTO {
	private String docName;
	private String specialization;

	public DoctorInfoDTO(String docName, String specialization) {
		super();
		this.docName = docName;
		this.specialization = specialization;
	}

	public String getDocName() {
		return docName;
	}

	public String getSpecialization() {
		return specialization;
	}

	@Override
	public String toString() {
		return docName + " " + specialization;
	}

}