package com.pramati.bot.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pramati.bot.service.GreetingService;

@Service
public class ClientGreeting {

	@Autowired
	private GreetingService greetingService;

	public String greetIntent() {
		return greetingService.greetIntent();
	}

	public String byeIntent() {
		return greetingService.byeIntent();
	}

}
