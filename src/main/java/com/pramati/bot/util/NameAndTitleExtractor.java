package com.pramati.bot.util;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

@Service
public class NameAndTitleExtractor {

	@Autowired
	private RestTemplate restTemplate;

	private static StanfordCoreNLP pipeline;
	static {

		Properties props = new Properties();
		props.setProperty("truecase.overwriteText", "true");
		props.setProperty("annotators", "tokenize,ssplit,truecase,pos,lemma,ner,entitymentions");
		pipeline = new StanfordCoreNLP(props);
	}

	public String getName(String text) {
		String name = getNameUsingStanfordModels(text);
		if (name.equalsIgnoreCase("Name not found"))
			name = getNameUsingSpacyModels(text);

		return name;
	}

	public String getNameUsingSpacyModels(String text) {
		String url = "http://127.0.0.1:5000/getname/";
		ResponseEntity<String> response = restTemplate.getForEntity(url + text, String.class);
		return response.getBody();
	}

	public String getNameUsingStanfordModels(String text) {
		Annotation document = new Annotation(text);
		pipeline.annotate(document);

		for (CoreMap sentence : document.get(CoreAnnotations.SentencesAnnotation.class)) {
			for (CoreMap entityMention : sentence.get(CoreAnnotations.MentionsAnnotation.class)) {
				if (entityMention.get(CoreAnnotations.EntityTypeAnnotation.class).equals("PERSON"))
					return entityMention.toString();
			}
		}

		return "Name not found";
	}

	public String getTitle(String text) {
		Annotation document = new Annotation(text);
		pipeline.annotate(document);

		for (CoreMap sentence : document.get(CoreAnnotations.SentencesAnnotation.class)) {
			for (CoreMap entityMention : sentence.get(CoreAnnotations.MentionsAnnotation.class)) {
				if (entityMention.get(CoreAnnotations.EntityTypeAnnotation.class).equals("TITLE")) {
					if (entityMention.toString().equalsIgnoreCase("doctor"))
						continue;
					return entityMention.toString();
				}
			}
		}

		return "Title not found";
	}

	public static void main(String[] args) {
		NameAndTitleExtractor nameExtractor = new NameAndTitleExtractor();
		System.out.println(nameExtractor.getTitle("get details of doctor srinivas"));
		System.out.println(nameExtractor.getName("when doctor sachin would be available"));
		System.out.println(nameExtractor.getName("vyshnavi"));
		System.out.println(nameExtractor.getName("vivek"));
		System.out.println(nameExtractor.getName("display the info of doctor sachin"));
		System.out.println(nameExtractor.getName("sandeep"));

	}

}
