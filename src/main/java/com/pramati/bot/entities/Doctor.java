package com.pramati.bot.entities;

import java.util.List;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

import com.pramati.bot.dto.DoctorInfoDTO;

@NamedNativeQuery(
		name = "DoctorInfoDTO", 
		query = "select doc_name,specialization from doctor where doc_name=:name", 
		resultSetMapping = "DoctorInfoDTO"
	)
	@SqlResultSetMapping(
			name = "DoctorInfoDTO",
			classes = @ConstructorResult(
					targetClass = DoctorInfoDTO.class, 
					columns = {
							@ColumnResult(name = "doc_name"),
							@ColumnResult(name = "specialization")
							}
					)
			)
@NamedNativeQuery(
		name = "getDoctors", 
		query = "select doc_name,specialization from doctor", 
		resultSetMapping = "DoctorInfoDTO"
	)
	@SqlResultSetMapping(
			name = "doctors",
			classes = @ConstructorResult(
					targetClass = DoctorInfoDTO.class, 
					columns = {
							@ColumnResult(name = "doc_name"),
							@ColumnResult(name = "specialization")
							}
					)
			)
@NamedNativeQuery(
		name = "getDoctorsBySpecialization", 
		query = "select doc_name,specialization from doctor where specialization=:specialization", 
		resultSetMapping = "DoctorInfoDTO"
	)
	@SqlResultSetMapping(
			name = "doctorsBySpecialization",
			classes = @ConstructorResult(
					targetClass = DoctorInfoDTO.class, 
					columns = {
							@ColumnResult(name = "doc_name"),
							@ColumnResult(name = "specialization")
							}
					)
			)
@Entity
@Table(name = "doctor")
public class Doctor {
	@Id
	private int docId;

	private String docName;
	private String specialization;

	@ManyToMany
	private List<Appointment> appointments;

	public List<Appointment> getAppointment() {
		return appointments;
	}

	public void setAppointment(List<Appointment> appointments) {
		this.appointments = appointments;
	}

	public int getDocId() {
		return docId;
	}

	public void setDocId(int docId) {
		this.docId = docId;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

}
