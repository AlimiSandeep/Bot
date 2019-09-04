package com.pramati.bot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pramati.bot.dao.AppointmentDao;

@Service
public class AppointmentService {

	@Autowired
	AppointmentDao dao;

	public int createAppointment(int doc_id, String slot_time, String appointment_date, int pid) {
		return dao.createAppointment(doc_id, slot_time, appointment_date, pid);
	}

	public List<Object[]> getAppointments(String appointment_date) {
		return dao.getAppointments(appointment_date);
	}
}
