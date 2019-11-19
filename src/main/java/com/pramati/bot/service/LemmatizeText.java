package com.pramati.bot.service;

import java.util.Properties;

import org.springframework.stereotype.Service;

import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

@Service
public class LemmatizeText {

	private static StanfordCoreNLP pipeline;

	static {
		Properties props = new Properties();
		props.setProperty("truecase.overwriteText", "true");
		props.setProperty("annotators", "tokenize,ssplit,truecase,pos,lemma,ner,entitymentions");

		pipeline = new StanfordCoreNLP(props);
	}

	public String getLemmatizedText(String input) {
		Annotation document = pipeline.process(input);
		StringBuffer lemmatizedText = new StringBuffer();
		for (CoreMap sentence : document.get(SentencesAnnotation.class)) {
			for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
				String lemma = token.get(LemmaAnnotation.class);

				lemmatizedText.append(lemma).append(" ");
			}
		}
		return lemmatizedText.toString();
	}
}
