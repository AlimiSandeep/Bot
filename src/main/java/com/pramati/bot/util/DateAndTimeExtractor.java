package com.pramati.bot.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.AnnotationPipeline;
import edu.stanford.nlp.pipeline.POSTaggerAnnotator;
import edu.stanford.nlp.pipeline.TokenizerAnnotator;
import edu.stanford.nlp.pipeline.WordsToSentencesAnnotator;
import edu.stanford.nlp.time.SUTime.Temporal;
import edu.stanford.nlp.time.TimeAnnotations;
import edu.stanford.nlp.time.TimeAnnotator;
import edu.stanford.nlp.time.TimeExpression;
import edu.stanford.nlp.util.CoreMap;

@Service
public class DateAndTimeExtractor {

	private static AnnotationPipeline pipeline;
	private static String referenceDate;

	static {
		Properties props = new Properties();
		pipeline = new AnnotationPipeline();
		pipeline.addAnnotator(new TokenizerAnnotator(false));
		pipeline.addAnnotator(new WordsToSentencesAnnotator(false));
		pipeline.addAnnotator(new POSTaggerAnnotator(false));
		pipeline.addAnnotator(new TimeAnnotator("sutime", props));

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		referenceDate = dateFormat.format(new Date());
	}

	public String getDate(String text) {

		String dateExpression = null;

		Annotation annotation = new Annotation(text);
		annotation.set(CoreAnnotations.DocDateAnnotation.class, referenceDate);
		pipeline.annotate(annotation);
//		System.out.println(annotation.get(CoreAnnotations.TextAnnotation.class));
		List<CoreMap> timexAnnsAll = annotation.get(TimeAnnotations.TimexAnnotations.class);
		for (CoreMap cm : timexAnnsAll) {
			Temporal temporal = cm.get(TimeExpression.Annotation.class).getTemporal();
			dateExpression = cm.get(TimeExpression.Annotation.class).getTemporal().toString();
			if (temporal.getTimexType().name().equalsIgnoreCase("TIME"))

				return dateExpression.substring(0, dateExpression.indexOf('T'));

			if (temporal.getTimexType().name().equalsIgnoreCase("DATE")) {
				Pattern p = Pattern.compile("W([0-4]?[0-9]{1}|5[0-3]{1})-WE$");
				Matcher m = p.matcher(dateExpression);
				if (m.find()) {
					return getDateFromWeek(dateExpression);
				}
				return dateExpression;
			}

		}

		return "Date not found";
	}

	public String getDateFromWeek(String str) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		int num = Integer.parseInt(str.substring(str.indexOf("W") + 1, str.indexOf("W") + 3));
		cal.set(Calendar.WEEK_OF_YEAR, num);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		return formatter.format(cal.getTime());

	}

	public String getTime(String text) {

		String dateExpression = null;

		Annotation annotation = new Annotation(text);
		annotation.set(CoreAnnotations.DocDateAnnotation.class, referenceDate);
		pipeline.annotate(annotation);
//		System.out.println(annotation.get(CoreAnnotations.TextAnnotation.class));
		List<CoreMap> timexAnnsAll = annotation.get(TimeAnnotations.TimexAnnotations.class);
		for (CoreMap cm : timexAnnsAll) {
			Temporal temporal = cm.get(TimeExpression.Annotation.class).getTemporal();
			dateExpression = cm.get(TimeExpression.Annotation.class).getTemporal().toString();
			if (temporal.getTimexType().name().equalsIgnoreCase("TIME"))
				return dateExpression.substring(dateExpression.indexOf('T') + 1, dateExpression.length());

		}

		return "Time not found";
	}

	public static void main(String[] args) {
		DateAndTimeExtractor dateAndTimeextractor = new DateAndTimeExtractor();
		System.out.println(dateAndTimeextractor.getDate("when doctor sachn would be availe tomorrow"));
		System.out.println(dateAndTimeextractor.getDate("when doctor sachn would be available on this weekend"));
		System.out.println(dateAndTimeextractor.getDate("Book an appointment with doctor sachin  today"));
		System.out.println(dateAndTimeextractor.getTime("Book an appointment with doctor sachin on 25th nov"));
		System.out.println(dateAndTimeextractor.getDate("get me available slots of doctor sachin"));
	}

}
