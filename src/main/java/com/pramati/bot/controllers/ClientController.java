package com.pramati.bot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pramati.bot.service.ClientService;

@RestController
public class ClientController {

	@Autowired
	private ClientService clientService;
	
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	public String getUserResponse(@RequestParam String userQuery) throws Exception {
		return clientService.getResponse(userQuery);
	}
	
	
	
	
}
