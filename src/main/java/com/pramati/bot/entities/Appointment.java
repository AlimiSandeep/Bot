package com.pramati.bot.entities;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToOne;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

import com.pramati.bot.dto.AppointmentInfoDTO;


@NamedNativeQuery(
	    name = "getAppointmentsByDate",
	    query ="select p.name,d.doc_name,a.appointment_date,s.slot_time from patient p,doctor d,slot s ,appointment a where a.pid=p.pid "
				+ "and a.doc_id=d.doc_id and a.slot_id=s.slot_id and a.appointment_date=:date",
	    resultSetMapping = "AppointmentInfoDTO"
	)
	@SqlResultSetMapping(
	    name = "allAppointments",
	    classes = @ConstructorResult(
	        targetClass = AppointmentInfoDTO.class,
	        columns = {
	            @ColumnResult(name = "name"),
	            @ColumnResult(name = "doc_name"),
	            @ColumnResult(name = "appointment_date"),
	            @ColumnResult(name = "slot_time")
	        }
	    )
	)

@NamedNativeQuery(
	    name = "getAppointmentByID",
	    query ="select p.name,d.doc_name,a.appointment_date,s.slot_time from patient p,doctor d,slot s,appointment a "
				+ "where a.pid=p.pid and a.doc_id=d.doc_id and a.slot_id=s.slot_id and a.appointment_id=:appointmentId",
	    resultSetMapping = "AppointmentInfoDTO"
	)
	@SqlResultSetMapping(
	    name = "singleAppointment",
	    classes = @ConstructorResult(
	        targetClass = AppointmentInfoDTO.class,
	        columns = {
	            @ColumnResult(name = "name"),
	            @ColumnResult(name = "doc_name"),
	            @ColumnResult(name = "appointment_date"),
	            @ColumnResult(name = "slot_time")
	        }
	    )
	)
@NamedNativeQuery(
	    name = "listAppointmentsByName",
	    query ="select p.name,d.doc_name,a.appointment_date,s.slot_time from appointment a,patient p,doctor d,slot s where a.pid=p.pid "
				+ "and a.doc_id=d.doc_id and a.slot_id=s.slot_id and a.pid=:pid",
	    resultSetMapping = "AppointmentInfoDTO"
	)
	@SqlResultSetMapping(
	    name = "AppointmentInfoDTO",
	    classes = @ConstructorResult(
	        targetClass = AppointmentInfoDTO.class,
	        columns = {
	            @ColumnResult(name = "name"),
	            @ColumnResult(name = "doc_name"),
	            @ColumnResult(name = "appointment_date"),
	            @ColumnResult(name = "slot_time")
	        }
	    )
	)
@Entity
@Table(name = "appointment")
public class Appointment {

	@Id
	private int appointmentId;

	@ManyToOne
	private Doctor doctor;

	@OneToOne
	private Slot slot;

	@ManyToOne
	private Patient patient;

	private String status;

	@Column(name = "appointment_date")
	private String date;

	public int getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(int appointmentId) {
		this.appointmentId = appointmentId;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public Slot getSlot() {
		return slot;
	}

	public void setSlot(Slot slot) {
		this.slot = slot;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
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
