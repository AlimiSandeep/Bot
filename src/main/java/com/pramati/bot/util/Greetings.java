package com.pramati.bot.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class Greetings {

	private static List<String> greetList;

	private static List<String> byeList;

	public Greetings() {
		greetList = new ArrayList<String>();
		byeList = new ArrayList<String>();

		greetList.add("Hi");
		greetList.add("Hello");
		greetList.add("Hello,How can i help you");
		greetList.add("Hie,Good morning");

		byeList.add("Bye bye");
		byeList.add("Ciao....Bye");
		byeList.add("Good to chat with you....Bye");
		byeList.add("Hope you are satisfied with our service....Bye");
	}

	public String greetIntent() {
		Random rand = new Random();
		return greetList.get(rand.nextInt(greetList.size()));
	}

	public String byeIntent() {
		Random rand = new Random();
		return byeList.get(rand.nextInt(byeList.size()));
	}
}
