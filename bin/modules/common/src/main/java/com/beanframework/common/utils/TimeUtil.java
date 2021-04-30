package com.beanframework.common.utils;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Period;

public class TimeUtil {

	public static String getTimeAgo(Date oldDate) {

		Period period = new Period(new DateTime(oldDate), new DateTime());

		int seconds = period.getSeconds();
		int minutes = period.getMinutes();
		int hours = period.getHours();
		int days = period.getDays();
		int weeks = period.getWeeks();
		int months = period.getMonths();
		int years = period.getYears();

		String elapsedTime = "";
		if (years != 0)
			if (years == 1)
				elapsedTime = years + " year ago";
			else
				elapsedTime = years + " years ago";
		else if (months != 0)
			if (months == 1)
				elapsedTime = months + " month ago";
			else
				elapsedTime = months + " months ago";
		else if (weeks != 0)
			if (weeks == 1)
				elapsedTime = weeks + " week ago";
			else
				elapsedTime = weeks + " weeks ago";
		else if (days != 0)
			if (days == 1)
				elapsedTime = days + " day ago";
			else
				elapsedTime = days + " days ago";
		else if (hours != 0)
			if (hours == 1)
				elapsedTime = hours + " hour ago";
			else
				elapsedTime = hours + " hours ago";
		else if (minutes != 0)
			if (minutes == 1)
				elapsedTime = minutes + " minute ago";
			else
				elapsedTime = minutes + " minutes ago";
		else if (seconds != 0)
			if (seconds == 1)
				elapsedTime = seconds + " second ago";
			else
				elapsedTime = seconds + " seconds ago";

		return elapsedTime;
	}
}
