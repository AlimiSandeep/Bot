package com.pramati.bot.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public class PatientsDao {
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public PatientsDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

//	Getting all patients
	public String getPatients() {
		String query = "select name,contact,city from patients";
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(query);
		String res = "";
		while (rowSet.next()) {
			res += rowSet.getString(1) + " " + rowSet.getString(2) + " " + rowSet.getString(3) + "\n";
		}

		return res;
	}

//	Getting all appointments details(patient_name,appointment_date,doc_name,time_of_appointment) for a patient name
	public String getPatientAppointments(@RequestParam String name) {

		String pid_query = "select pid from patients where name=?";
		SqlRowSet rowset_name = jdbcTemplate.queryForRowSet(pid_query, name);
		rowset_name.next();
		int pid = rowset_name.getInt(1);

		String query = "select p.name,d.doc_name,a.appointment_date,s.slot_time from patients p,doctors d,slots s,appointments a where a.pid=p.pid "
				+ "and a.doc_id=d.doc_id and a.slot_id=s.slot_id and a.pid=?";
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(query, pid);
		String res = "";
		while (rowSet.next()) {
//			System.out.println(rowSet.getString(1)+" "+rowSet.getString(2)+" "+rowSet.getString(3));
			res += rowSet.getString(1) + " " + rowSet.getString(2) + " " + rowSet.getString(3) + " "
					+ rowSet.getString(4) + "\n";
		}

		return res;

	}

	public int newPatient(String name, int contact, String city) {
		String query = "insert into patients(name,contact,city) values(?,?,?)";
		int updateCount = 0;
		try {
			updateCount = jdbcTemplate.update(query, name, contact, city);
		} catch (Exception e) {
//			System.out.println("In exception " + e.getStackTrace());
		}
		return updateCount;

	}

	public int deletePatient(String name) {
		String query = "delete from patients where name=?";
		int flag = 0;
		try {
			flag = jdbcTemplate.update(query, name);
		} catch (Exception e) {

		}
		return flag;
	}
}
