package com.pramati.bot.service;

import java.util.Properties;

import org.springframework.stereotype.Service;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

@Service
public class NameAndTitleExtractor {

	/*
	 * public String getName(String text) { String intent = null; try {
	 * 
	 * text = text.trim().replace(" ", "%20"); URL url = new
	 * URL("http://127.0.0.1:5000/getname/" + text); HttpURLConnection conn =
	 * (HttpURLConnection) url.openConnection(); conn.setRequestMethod("GET");
	 * 
	 * conn.setRequestProperty("Accept", "application/json"); if
	 * (conn.getResponseCode() != 200) { throw new
	 * RuntimeException("Failed : HTTP Error code : " + conn.getResponseCode()); }
	 * InputStreamReader in = new InputStreamReader(conn.getInputStream());
	 * BufferedReader br = new BufferedReader(in);
	 * 
	 * intent = br.readLine(); conn.disconnect();
	 * 
	 * } catch (Exception e) { System.out.println("Exception in NetClientGet:- " +
	 * e); }
	 * 
	 * return intent; }
	 */

	private static StanfordCoreNLP pipeline;
	static {

		Properties props = new Properties();
		props.setProperty("truecase.overwriteText", "true");
		props.setProperty("annotators", "tokenize,ssplit,truecase,pos,lemma,ner,entitymentions");
		pipeline = new StanfordCoreNLP(props);
	}

	public String getName(String text) {
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
					if(entityMention.toString().equalsIgnoreCase("doctor"))
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
		System.out.println(nameExtractor.getName("get the available slots for sandeep"));
//		System.out.println(nameExtractor.getName("vyshnavi"));
//		System.out.println(nameExtractor.getName("vivek"));
//		System.out.println(nameExtractor.getName("sachin"));
//		System.out.println(nameExtractor.getName("sandeep"));
		
	}

}
