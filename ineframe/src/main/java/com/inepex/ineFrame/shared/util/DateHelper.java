package com.inepex.ineFrame.shared.util;

import java.util.Date;

import com.inepex.ineFrame.client.i18n.IneFrameI18n;

public class DateHelper {
	
	public static final long secondInMs = 1000;
	public static final long minuteInMs = 60 * secondInMs;
	public static final long hourInMs = 60 * minuteInMs;
	public static final long dayInMs = 24 * hourInMs;

	@SuppressWarnings("deprecation")
	public static int getYear(Date date) {
		if (date == null)
			throw new IllegalArgumentException();
		return date.getYear() + 1900;
	}
	
	@SuppressWarnings("deprecation")
	public static int getMonth(Date date) {
		if (date == null)
			throw new IllegalArgumentException();
		return date.getMonth() + 1;
	}
	
	@SuppressWarnings("deprecation")
	public static Date resetHourMinuteSecond(Date date) {
		if (date == null)
			return date;
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);
		return date;
	}
	
	@SuppressWarnings("deprecation")
	public static long getFirstMSOfDay(long date) {
		Date d = new Date(date);
		d.setHours(0);
		d.setMinutes(0);
		d.setSeconds(0);
		return (d.getTime()/1000)*1000+1;
	}
	
	@SuppressWarnings("deprecation")
	public static long getNoon(long date) {
		Date d = new Date(date);
		d.setHours(12);
		d.setMinutes(0);
		d.setSeconds(0);
		return (d.getTime()/1000)*1000;
	}
	
	@SuppressWarnings("deprecation")
	public static long getLastMSOfDay(long date) {
		Date d = new Date(date);
		d.setHours(23);
		d.setMinutes(59);
		d.setSeconds(59);
		return (d.getTime()/1000)*1000+999;
	}

	@SuppressWarnings("deprecation")
	public static Date resetSecond(Date date) {
		if (date == null)
			return date;
		date.setSeconds(0);
		date.setTime((date.getTime() / 1000L) * 1000L);
		return date;
	}

	/**
	 * 
	 * WARNING! The methods behave changed... doesn't modify param date anymore
	 * 
	 */
	@SuppressWarnings("deprecation")
	public static Date addDaysSafe(Date date, int days) {
		if (date == null)
			return date;
		int day = date.getDate();
		Date dateCopy = new Date(date.getTime());
		dateCopy.setDate(day + days); // the Date class handles it correctly even if the month doesn't have that many days as it is set in the argument (it adjusts the year, month and day if necessary)
		return dateCopy;
	}

	public static Date addHours(Date date, int hours) {
		return addHours(date, (long)hours);
	}

	public static Date addHours(Date date, long hours) {
		if (date == null)
			return date;
		return new Date(date.getTime() + hours * hourInMs);
	}

	public static Date addMinutes(Date date, int minutes) {
		return addMinutes(date, (long)minutes);
	}

	public static Date addMinutes(Date date, long minutes) {
		if (date == null)
			return date;
		return new Date(date.getTime() + minutes * minuteInMs);
	}

	public static Date addSeconds(Date date, int seconds) {
		return addSeconds(date, (long)seconds);
	}

	public static Date addSeconds(Date date, long seconds) {
		if (date == null)
			return date;
		return new Date(date.getTime() + seconds * secondInMs);
	}

	@SuppressWarnings("deprecation")
	public static Date getDate(int year, int month, int day, int hour, int minute, int second) {
		return new Date(year - 1900, month - 1, day, hour, minute, second);
	}

	public static Date copyDate(Date date) {
		return new Date(date.getTime());
	}

	public static Long diffMillis(Date startDate, Date endDate) {
		if (endDate == null || startDate == null)
			return null;
		return endDate.getTime() - startDate.getTime();
	}

	public static Long diffMinutes(Date startDate, Date endDate) {
		Long diffMillis = diffMillis(startDate, endDate);
		if (diffMillis == null)
			return null;
		return Math.round((double)diffMillis / minuteInMs);
	}

	public static Long diffHours(Date startDate, Date endDate) {
		Long diffMillis = diffMillis(startDate, endDate);
		if (diffMillis == null)
			return null;
		return Math.round((double)diffMillis / hourInMs);
	}
	
	public static Long diffMillis(Long startDateLong, Long endDateLong) {
		if (endDateLong == null || startDateLong == null)
			return null;
		return endDateLong - startDateLong;
	}

	public static Long diffMinutes(Long startDateLong, Long endDateLong) {
		Long diffMillis = diffMillis(startDateLong, endDateLong);
		if (diffMillis == null)
			return null;
		return Math.round((double)diffMillis / minuteInMs);
	}

	public static Long diffHours(Long startDateLong, Long endDateLong) {
		Long diffMillis = diffMillis(startDateLong, endDateLong);
		if (diffMillis == null)
			return null;
		return Math.round((double)diffMillis / hourInMs);
	}
	
	/**
	 * example:
	 * 
	 * 2010.11.17-2010.11.17		1 day
	 * 2010.11.17-2010.11.18		2 days
	 */
	public static Long diffDaysInclusive(Date startDate, Date endDate) {
		Long diffMillis = diffMillis(resetHourMinuteSecond(startDate), resetHourMinuteSecond(endDate));
		if (diffMillis == null)
			return null;
		return Math.round((double)diffMillis / dayInMs) + 1;
	}

	/**
	 * example:
	 * <br/>
	 * 2010.11.17-2010.11.17		1 day <br/>
	 * 2010.11.17-2010.11.18		2 days <br/>
	 */
	public static Long diffDaysInclusive(Long startDateLong, Long endDateLong) {
		return diffDaysInclusive(new Date(startDateLong), new Date(endDateLong));
	}

	@SuppressWarnings("deprecation")
	public static Date setHours(Date date, int hours) {
		if (date != null)
			date.setHours(hours);
		return date;
	}
	
	@SuppressWarnings("deprecation")
	public static Date resetHoursMinsSecsMillis(Date d){
		Date rounded = new Date(d.getYear(), d.getMonth(), d.getDate());
		return rounded;
	}
	
	@SuppressWarnings("deprecation")
	public static Date getNextWeekend(Date from){
		Date date = resetHoursMinsSecsMillis(from);
		date = addDaysSafe(date, 1);
		while (date.getDay() != 6){
			date = addDaysSafe(date, 1);
		}
		return date;		
	}
	
	@SuppressWarnings("deprecation")
	public static Date getNextWeekStartDate(Date from){
		Date date = resetHoursMinsSecsMillis(from);
		date = addDaysSafe(date, 1);
		while (date.getDay() != 1){
			date = addDaysSafe(date, 1);
		}
		return date;		
	}
	
	@SuppressWarnings("deprecation")
	public static Date getThisWeekStartDate(Date from){
		Date date = resetHoursMinsSecsMillis(from);
		while (date.getDay() != 1){
			date = addDaysSafe(date, -1);
		}
		return date;		
	}
	
	@SuppressWarnings("deprecation")
	public static Date getPrevWeekStartDate(Date from){
		Date date = resetHoursMinsSecsMillis(from);
		date = addDaysSafe(date, -7);
		while (date.getDay() != 1){
			date = addDaysSafe(date, -1);
		}
		return date;		
	}
	
	@SuppressWarnings("deprecation")
	public static Date getNextMonthStartDate(Date from){
		Date date = resetHoursMinsSecsMillis(from);
		date.setMonth(date.getMonth() + 1);
		date.setDate(1);
		return date;		
	}
	
	@SuppressWarnings("deprecation")
	public static Date getThisMonthStartDate(Date from){
		Date date = resetHoursMinsSecsMillis(from);
		date.setDate(1);
		return date;		
	}
	
	@SuppressWarnings("deprecation")
	public static Date getPrevMonthStartDate(Date from){
		Date date = resetHoursMinsSecsMillis(from);
		date.setMonth(date.getMonth() - 1);
		date.setDate(1);
		return date;		
	}
	
	@SuppressWarnings("deprecation")
	public static Date getMonthEndDate(Date dateInMonth){
		Date endDate = resetHoursMinsSecsMillis(dateInMonth);
		int month = endDate.getMonth();
		do {
			endDate = addDaysSafe(endDate, 1);
		} while (endDate.getMonth() == month);
		endDate = addDaysSafe(endDate, -1);
		return endDate;
	}
	
	@SuppressWarnings("deprecation")
	public static Date getNextQuarterStart(Date from){
		int nextQuarter = getQuarterOfDate(from) + 1;
		int year = from.getYear();
		if (nextQuarter == 5) {
			nextQuarter = 1;
			year++;
		}
		switch (nextQuarter){
		case 1: return new Date(year, 0, 1);
		case 2: return new Date(year, 3, 1);
		case 3: return new Date(year, 6, 1);
		case 4: return new Date(year, 9, 1);
		default: return null;
		}
	}
	
	public static int getNextQuarterOfDate(Date d){
		int nextQuarter = getQuarterOfDate(d) + 1;
		if (nextQuarter == 5) {
			nextQuarter = 1;
		}
		return nextQuarter;
	}
	
	@SuppressWarnings("deprecation")
	public static int getQuarterOfDate(Date d){
		switch (d.getMonth()){
			case 0: return 1;
			case 1: return 1;
			case 2: return 1;
			case 3: return 2;
			case 4: return 2;
			case 5: return 2;
			case 6: return 3;
			case 7: return 3;
			case 8: return 3;
			case 9: return 4;
			case 10: return 4;
			case 11: return 4;
			default: return -1;
		}
	}
	
	@SuppressWarnings("deprecation")
	public static Date getNextHalfYearStartDate(Date from){
		int nextHalf = getHalfYearOfDate(from) + 1;
		int year = from.getYear();
		if (nextHalf == 3) {
			nextHalf = 1;
			year++;
		}
		switch (nextHalf){
		case 1: return new Date(year, 0, 1);
		case 2: return new Date(year, 6, 1);
		default: return null;
		}
	}
	
	@SuppressWarnings("deprecation")
	public static int getHalfYearOfDate(Date d){
		if (d.getMonth() <=5) return 1;
		else return 2;
	}
	
	@SuppressWarnings("deprecation")
	public static Date addMonth(Date date, int months) {
		if (date == null)
			return date;
		Date tmpDate = new Date(date.getTime());
		tmpDate.setMonth(date.getMonth()+ months);
		
		int monthsOfYearDiff = (tmpDate.getYear() - date.getYear()) * 12;
		while ((tmpDate.getMonth() + monthsOfYearDiff) != date.getMonth() + months){
			tmpDate.setDate(tmpDate.getDate() - 1);
		}
		return tmpDate;
	}

	public static Long nowLong() {
		return new Date().getTime();
	}
	
	/**
	 * @param duration in milliseconds
	 * @param showSec if true seconds is showed, if false the value of seconds will be rounded into minutes (0&lt;=seconds&lt;30 +0min, 30&lt;=seconds&lt;60 +1min)
	 * @return formatted duration, like 3h 34m
	 */
	public static String formatDuration(long duration, boolean showSec){
		if(!showSec) {
			long secAndMs = duration%minuteInMs;
			if(secAndMs >= 30*secondInMs) {
				duration=duration-secAndMs+minuteInMs;
			}
		}
			
		
		int days = (int) (duration / dayInMs);
		duration -= days * dayInMs;
		
		int hours = (int) (duration / hourInMs);
		duration -= hours * hourInMs;
		
		int minutes = (int) (duration / minuteInMs);
		duration -= minutes * minuteInMs;
		
		int seconds = (int) (duration / secondInMs);
		
		StringBuffer sb = new StringBuffer();
		if (days > 0) {
			sb.append(days);
			sb.append(IneFrameI18n.dayShort());
			sb.append(" ");
		}
		if (hours > 0 || days > 0) {
			sb.append(hours);
			sb.append(IneFrameI18n.hourShort());
			sb.append(" ");
		}
		
		sb.append(minutes);
		sb.append(IneFrameI18n.minShort());
		
		if (showSec) {
			sb.append(" ");
			sb.append(seconds);
			sb.append(IneFrameI18n.secShort());
		}
		
		return sb.toString();
		
	}
	public static Date getDayEndDate(Date date){
		date = resetHoursMinsSecsMillis(date);
		date = addDaysSafe(date, 1);
		date.setTime(date.getTime() - 1);
		return date;
	}
	
	public static Date getToday(DateProvider dateProvider){
		Date today = dateProvider.whatMeansTyped(new Date().getTime());
		today.setHours(12);
		today.setMinutes(1);
		return today;
	}

}