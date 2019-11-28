package com.pramati.bot.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class NumberValidator {

	public boolean validateContactNumber(String contact) {
		Pattern p = Pattern.compile("^[7-9]{1}\\d{9}$");
		Matcher m = p.matcher(contact);
		return m.find();
	}
}
