package com.pramati.bot.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
<<<<<<< HEAD
import com.pramati.bot.service.DoctorService;
=======
import org.springframework.transaction.annotation.Transactional;

import com.pramati.bot.dto.AppointmentInfoDTO;
import com.pramati.bot.service.PatientService;
import com.pramati.bot.service.SlotService;
>>>>>>> 3390e22... used DTO's for projections

@Repository
public class AppointmentDao {
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public AppointmentDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Autowired
	private SlotService slotService;

<<<<<<< HEAD
	public String createAppointment(int doc_id, String slot_time, String appointment_date, int pid) {

		String check_query = "select count(b.status) from appointments b,slots s where b.slot_id=s.slot_id and s.slot_time=? and b.appointment_date=? and b.pid=?";
=======
	@Autowired
	private PatientService patientService;

	@Transactional
	public int createAppointment(int docId, String slotTime, String appointmentDate, int pId) {
		int flag = checkAppointmentExists(slotTime, appointmentDate, pId);
		if (flag < 1) {
			int slotId = slotService.getSlotId(slotTime);
			String query = "insert into appointment(doc_id,slot_id,status,appointment_date,pid) values(:doc_id,:slot_id,:status,:appointment_date,:pid)";
			return entityManager.createNativeQuery(query).setParameter("doc_id", docId).setParameter("slot_id", slotId)
					.setParameter("status", "Y").setParameter("appointment_date", appointmentDate)
					.setParameter("pid", pId).executeUpdate();
		}
		return 0;
	}
>>>>>>> 3390e22... used DTO's for projections

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
<<<<<<< HEAD
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

=======
	public List<AppointmentInfoDTO> getAppointments(String appointmentDate) {

		return entityManager.createNamedQuery("getAppointmentsByDate").setParameter("date", appointmentDate)
				.getResultList();
	}

	public AppointmentInfoDTO getAppointment(int appointmentId) {
		try {
			return (AppointmentInfoDTO) entityManager.createNamedQuery("getAppointmentByID")
					.setParameter("appointmentId", appointmentId).getSingleResult();
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	public List<AppointmentInfoDTO> getPatientAppointments(String name) {

		int pid = patientService.getPatientId(name);

		return entityManager.createNamedQuery("listAppointmentsByName").setParameter("pid", pid).getResultList();

	}

	public int checkAppointmentsExistsOnDate(String appointmentDate) {
		String query = "select count(appointment_id) from appointment where appointment_date=:appointmentDate";
		BigInteger count = (BigInteger) entityManager.createNativeQuery(query)
				.setParameter("appointmentDate", appointmentDate).getSingleResult();
		return count.intValue();
>>>>>>> 3390e22... used DTO's for projections
	}

	@Transactional
	public int deleteAppointment(int appointmentId) {
		String query = "delete from appointment where appointment_id=:id";
		return entityManager.createNativeQuery(query).setParameter("id", appointmentId).executeUpdate();
	}
}
