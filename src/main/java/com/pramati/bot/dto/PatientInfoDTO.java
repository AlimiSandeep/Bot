package com.pramati.bot.dto;

public class PatientInfoDTO {
	private String name;
	private Integer contact;

	public PatientInfoDTO(String name, Integer contact) {
		super();
		this.name = name;
		this.contact = contact;

	}

	public void setName(String name) {
		this.name = name;
	}

	public void setContact(Integer contact) {
		this.contact = contact;
	}

	public String getName() {
		return name;
	}

	public Integer getContact() {
		return contact;
	}

	@Override
	public String toString() {
		return name + " " + contact;
	}

}
