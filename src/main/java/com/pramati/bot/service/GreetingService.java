package com.pramati.bot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pramati.bot.util.Greetings;

@Service
public class GreetingService {

	@Autowired
	private Greetings greeting;

	public String greetIntent() {

		return greeting.greetIntent();
	}

	public String byeIntent() {

		return greeting.byeIntent();
	}

}
