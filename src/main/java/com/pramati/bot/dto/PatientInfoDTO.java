package com.pramati.bot.dto;

public class PatientInfoDTO {
	private String name;
	private Integer contact;
	private String city;

	public PatientInfoDTO(String name, Integer contact, String city) {
		super();
		this.name = name;
		this.contact = contact;
		this.city = city;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setContact(Integer contact) {
		this.contact = contact;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getName() {
		return name;
	}

	public Integer getContact() {
		return contact;
	}

	public String getCity() {
		return city;
	}

	@Override
	public String toString() {
		return name + " " + contact + " " + city;
	}

}
