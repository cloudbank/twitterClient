package com.example.basictwitter;

import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.text.format.DateUtils;

class DateUtility {

	public static String twitterTime(String timestamp, Context c) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(timestamp));
		String s = DateUtils.getRelativeDateTimeString(c,
				cal.getTimeInMillis(), DateUtils.SECOND_IN_MILLIS,
				DateUtils.DAY_IN_MILLIS, 0).toString();
		s = s.substring(0, s.indexOf(','));
		s = manicureTime(s);
		return s;
	}

	private static String manicureTime(String s) {

		if (s.contains("seconds") || s.contains("second") ) {
			s = s.substring(0, s.indexOf("s") + 1);
		} else if (s.contains("minutes") || s.contains("minute")) {
			s = s.substring(0, s.indexOf("m") + 1);
		} else if (s.contains("hours") || s.contains("hour")) {
			s = s.substring(0, s.indexOf("h") + 1);
		}
		s = s.replaceAll(" ", "");
		if (s.contains("in")) {
			s = s.substring(s.indexOf("n")+1);
		}
		return s;
	}

}
