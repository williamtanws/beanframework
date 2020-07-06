package com.beanframework.cronjob.utils;

import java.util.Calendar;
import java.util.Date;

public class CronUtil {

	private final Date mDate;
	private final Calendar mCal;
	private final String mSeconds = "0";
	private final String mDaysOfWeek = "?";

	private String mMins;
	private String mHours;
	private String mDaysOfMonth;
	private String mMonths;
	private String mYears;

	public CronUtil(Date pDate) {
		this.mDate = pDate;
		mCal = Calendar.getInstance();
		this.generateCronExpression();
	}

	private void generateCronExpression() {
		mCal.setTime(mDate);

		String hours = String.valueOf(mCal.get(Calendar.HOUR_OF_DAY));
		this.mHours = hours;

		String mins = String.valueOf(mCal.get(Calendar.MINUTE));
		this.mMins = mins;

		String days = String.valueOf(mCal.get(Calendar.DAY_OF_MONTH));
		this.mDaysOfMonth = days;

		String months = new java.text.SimpleDateFormat("MM").format(mCal.getTime());
		this.mMonths = months;

		String years = String.valueOf(mCal.get(Calendar.YEAR));
		this.mYears = years;

	}

	public Date getDate() {
		return mDate;
	}

	public String getSeconds() {
		return mSeconds;
	}

	public String getMins() {
		return mMins;
	}

	public String getDaysOfWeek() {
		return mDaysOfWeek;
	}

	public String getHours() {
		return mHours;
	}

	public String getDaysOfMonth() {
		return mDaysOfMonth;
	}

	public String getMonths() {
		return mMonths;
	}

	public String getYears() {
		return mYears;
	}

}