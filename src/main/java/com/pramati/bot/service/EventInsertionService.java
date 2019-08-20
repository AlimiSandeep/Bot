package com.pramati.bot.service;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.springframework.stereotype.Service;

@Service
public class EventInsertionService {

//	@Autowired
//	private static CalendarAPI api;
	private static Calendar service;

	private static DateTime now = new DateTime(System.currentTimeMillis());

	static {
		try {
			service = CalendarAPI.getService();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
	}

	public boolean createEvent(String startDate, String endDate) throws IOException {

		Event event = new Event().setSummary("Appointment");

		DateTime startDateTime = new DateTime(startDate);
		EventDateTime start = new EventDateTime().setDateTime(startDateTime).setTimeZone("Asia/Kolkata");
		event.setStart(start);

		DateTime endDateTime = new DateTime(endDate);
		EventDateTime end = new EventDateTime().setDateTime(endDateTime).setTimeZone("Asia/Kolkata");
		event.setEnd(end);

		if (checkEventExists(startDate)) {

			return true;
		}
		return false;
	}

	public boolean checkEventExists(String startDate) throws IOException {

		List<Event> items = getEvents();

		if (items.isEmpty()) {
			return true;
		} else {
			for (Event event : items) {

				String eventDate = event.getStart().getDateTime().toString();
				if (event.getSummary().equals("NoAppointment") && eventDate.equals(startDate)) {
					updateEvent(event);

					return true;
				}

			}
		}
		return false;
	}

	public void updateEvent(Event event) throws IOException {

		event.setSummary("Appointment");
		service.events().update("primary", event.getId(), event).execute();

	}

	public List<String> suggestEvents() throws IOException, ParseException {

		List<String> availableSlots = new ArrayList<String>();
		List<Event> items = getEvents();
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
		SimpleDateFormat df2 = new SimpleDateFormat("hh:mm aa");

		for (Event event : items) {
			String date = event.getStart().getDateTime().toString();
			if (!event.getSummary().equals("Appointment")) {
				Date strDate = df1.parse(date);
				date = df2.format(strDate);
				availableSlots.add(date);
//                System.out.println(date);
			}
		}
		return availableSlots;
	}

	public List<Event> getEvents() throws IOException {

		Events events = service.events().list("sandeep.alimi@imaginea.com").setMaxResults(10).setTimeMin(now)
				.setOrderBy("startTime").setSingleEvents(true).execute();
		List<Event> items = events.getItems();
		return items;
	}
}
