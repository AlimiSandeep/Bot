package com.pramati.bot.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import com.pramati.bot.service.DoctorService;

@Repository
public class AppointmentDao {
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public AppointmentDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Autowired
	private DoctorService docService;

	public String createAppointment(int doc_id, String slot_time, String appointment_date, int pid) {

		String check_query = "select count(b.status) from appointments b,slots s where b.slot_id=s.slot_id and s.slot_time=? and b.appointment_date=? and b.pid=?";

		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(check_query, slot_time, appointment_date, pid);
		rowSet.next();
		int count = rowSet.getInt(1);

		if (count < 1) {

			try {
				String slot_query = "select slot_id from slots where slot_time=?";
				SqlRowSet rowset_id = jdbcTemplate.queryForRowSet(slot_query, slot_time);
				rowset_id.next();
				int slot_id = rowset_id.getInt(1);

				String query = "insert into appointments(doc_id,slot_id,status,appointment_date,pid) values(?,?,?,?,?)";
				jdbcTemplate.update(query, doc_id, slot_id, "Y", appointment_date, pid);

				return "Appointment created successfully";
			} catch (Exception e) {

			}

		}
		return "No slots available at that time\nAvailable slots on " + appointment_date + " are ::" + "\n"
				+ docService.getAvailableSlotsForPatient(appointment_date, pid);

	}

//	list of appointments on a date
//	i.e Getting all appointments details(patient_name,appointment_date,doc_name,time_of_appointment) for a date
	public String getAppointments(String appointment_date) {
		String query = "select p.name,d.doc_name,s.slot_time from patients p,doctors d,slots s,appointments a where a.pid=p.pid "
				+ "and a.doc_id=d.doc_id and a.slot_id=s.slot_id and a.appointment_date=?";
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(query, appointment_date);
		String res = "";
		while (rowSet.next()) {
//			System.out.println(rowSet.getString(1)+" "+rowSet.getString(2)+" "+rowSet.getString(3));
			res += rowSet.getString(1) + " " + rowSet.getString(2) + " " + rowSet.getString(3) + "\n";
		}

		return res;

	}

}
