package com.pramati.bot.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pramati.bot.service.DoctorService;
import com.pramati.bot.util.LemmatizeText;
import com.pramati.bot.util.NameAndTitleExtractor;

@Service
public class ClientDoctorService {

	@Autowired
	private DoctorService docService;

	@Autowired
	private LemmatizeText lemmatiser;

	@Autowired
	private NameAndTitleExtractor nameAndTitleExtractor;

	public String doctorIntent(String userQuery) {

		String name = nameAndTitleExtractor.getName(userQuery);

		if (!name.equalsIgnoreCase("Name not found"))
			return docService.getDoctor(name);

		userQuery = lemmatiser.getLemmatizedText(userQuery);
		String specialization = nameAndTitleExtractor.getTitle(userQuery);

		if (!specialization.equalsIgnoreCase("Title not found")) {
			return docService.getDoctorsBySpecialization(specialization);
		}

		return docService.getDoctors().toString();
	}
}
