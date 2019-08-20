package com.pramati.bot.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.pramati.bot.service.IDoctorService;

@Component
public class DoctorsDao implements IDoctorService {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public DoctorsDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public int newDoctor(String name, String specialization) {
		String query = "insert into doctors(doc_name,specialization) values(?,?)";
		int updateCount = 0;
		try {
			updateCount = jdbcTemplate.update(query, name, specialization);
		} catch (Exception e) {
//			System.out.println("In exception " + e.getStackTrace());
		}
		return updateCount;

	}

	public String getDoctors() {
		String query = "select doc_name,specialization from doctors";
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(query);
		String res = "";
		while (rowSet.next()) {
			res += rowSet.getString(1) + " " + rowSet.getString(2) + "\n";
		}

		return res;
	}

	public String getAvailableSlots(String date, int doc_id) {
		String query = "select distinct s.slot_time from slots s INNER JOIN appointments b on b.slot_id!=s.slot_id and s.slot_id not in(select slot_id from appointments where appointment_date='"
				+ date + "' and doc_id='" + doc_id + "')";
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(query);
		String res = "";
		while (rowSet.next()) {
			res += rowSet.getString(1) + "\n";
		}

		return res;

	}

	public int deleteDoctor(String name) {
		String query = "delete from doctors where doc_name=?";
		int flag=0;
		try {
			flag=jdbcTemplate.update(query, name);
		}
		catch(Exception e) {
			
		}
		return flag;
	}

}
