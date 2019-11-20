package com.pramati.bot.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pramati.bot.util.DateAndTimeExtractor;
import com.pramati.bot.util.IntentExtractor;
import com.pramati.bot.util.NameAndTitleExtractor;

@Service
public class ClientService {

	@Autowired
	private ClientService clientService;

	@Autowired
	private IntentExtractor intentExtractor;

	@Autowired
	private NameAndTitleExtractor nameExtractor;

	@Autowired
	private DoctorService docService;

	@Autowired
	private DateAndTimeExtractor dateAndTimeExtractor;

	@Autowired
	private SlotService slotService;

	public String getResponse(String userQuery) throws ParseException {

		String intent = intentExtractor.getIntent(userQuery);

		switch (intent) {

		case "doctors":
			return clientService.doctorIntent(userQuery);
		case "slots":
			return clientService.slotIntent(userQuery);

		default:
			return "In default method";
		}

//		IntentHandler intentHandler = intentHandlerFactory.getIntentHandler(intent);
//		intentHandler.handleIntent(intent);
	}

	public String doctorIntent(String userQuery) {
		String name = nameExtractor.getName(userQuery);
		if (!name.equalsIgnoreCase("Name not found"))
			return docService.getDoctor(name);

		return docService.getDoctors().toString();
	}

	public String slotIntent(String userQuery) throws ParseException {
		String date = dateAndTimeExtractor.getDate(userQuery);
		String name = nameExtractor.getName(userQuery);
		if (date.equalsIgnoreCase("Date not found")) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			date = dateFormat.format(new Date());
		}

		return slotService.getAvailableSlots(date, name);

	}
}

//private static Map<String, Callable<String>> map;
//Callable<String> call= map.get(intent);
//return call.call();
//map.put("doctors", new Callable<String>() {
//
//	@Override
//	public String call() throws Exception {
//
//		return new ClientService().doctorIntent(userQuery);
//	}
//
//});
