package com.pramati.bot.service;



public interface IAppointmentService {
	public String createAppointment(int doc_id,String slot_time,String appointment_date, int pid) ;
	public String getAppointments(String appointment_date);
}
