package com.pramati.bot.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class IntentExtractor {

	@Autowired
	private RestTemplate restTemplate;

	public String getIntent(String userText) {

		String url = "http://127.0.0.1:5000//getintent/";
		ResponseEntity<String> response = restTemplate.getForEntity(url + userText, String.class);
		return response.getBody();
	}

}
