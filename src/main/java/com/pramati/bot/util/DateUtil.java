package com.pramati.bot.util;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.time.SUTime;
import edu.stanford.nlp.time.TimeAnnotations;
import edu.stanford.nlp.time.TimeAnnotator;
import edu.stanford.nlp.time.TimeExpression;
import edu.stanford.nlp.util.CoreMap;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.text.ParseException;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

@Service
public class DateUtil {

	public String getStartDate(String message) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		Date date = new Date();

		Properties props = new Properties();
		AnnotationPipeline pipeline = new AnnotationPipeline();

		pipeline.addAnnotator(new TokenizerAnnotator(false));
		pipeline.addAnnotator(new WordsToSentencesAnnotator(false));
		pipeline.addAnnotator(new POSTaggerAnnotator(false));
		pipeline.addAnnotator(new TimeAnnotator("sutime", props));

		Annotation annotation = new Annotation(message);
		annotation.set(CoreAnnotations.DocDateAnnotation.class, df.format(date));
		pipeline.annotate(annotation);
//	        System.out.println(annotation.get(CoreAnnotations.TextAnnotation.class));

		List<CoreMap> cm = annotation.get(TimeAnnotations.TimexAnnotations.class);
		SUTime.Temporal temporal = cm.get(0).get(TimeExpression.Annotation.class).getTemporal();

		return temporal.toString() + ":00.000+05:30";
	}

	public String getEndDate(String startDate) throws ParseException {
		DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date d1 = df1.parse(startDate);
		Date newDate = DateUtils.addMinutes(d1, 30);
		String date = df1.format(newDate);
		return date + ".000+05:30";

	}

	public String addDays(String date) throws ParseException {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date d = dateFormat.parse(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.DATE, 1); // minus number would decrement the days
		String addedDate = dateFormat.format(cal.getTime());
		return addedDate;
	}

	public boolean dateComparer(String date) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date d = dateFormat.parse(date);
		Date todaysDate = dateFormat.parse(dateFormat.format(new Date()));
		return (d.after(todaysDate) || d.equals(todaysDate));

	}

	public boolean dateIsSunday(String date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date d;
		try {
			d = dateFormat.parse(date);
		} catch (ParseException e) {
			return false;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
			return true;
		return false;
	}

}
