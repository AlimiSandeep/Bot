package com.pramati.bot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pramati.bot.dao.AppointmentDao;

@Service
public class AppointmentService {

	@Autowired
	AppointmentDao dao;

	public String createAppointment(int doc_id, String slot_time, String appointment_date, int pid) {
		return dao.createAppointment(doc_id, slot_time, appointment_date, pid);
	}

	public String getAppointments(String appointment_date) {
		return dao.getAppointments(appointment_date);
	}
}
