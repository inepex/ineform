package com.inepex.ineFrame.shared.util.date;

import com.inepex.ineFrame.client.i18n.IneFrameI18n;

public class DateHelper {
	
	public static final long secondInMs = 1000;
	public static final long minuteInMs = 60 * secondInMs;
	public static final long hourInMs = 60 * minuteInMs;
	public static final long dayInMs = 24 * hourInMs;

	public static long diffMillis(UserDate startDate, UserDate endDate) {
		return endDate.getGWTTime() - startDate.getGWTTime();
	}

	public static <T extends LocalDate<T>> long diffMinutes(UserDate startDate, UserDate endDate) {
		Long diffMillis = diffMillis(startDate, endDate);
		return Math.round((double)diffMillis / minuteInMs);
	}

	public static <T extends LocalDate<T>> long diffHours(UserDate startDate, UserDate endDate) {
		Long diffMillis = diffMillis(startDate, endDate);
		return Math.round((double)diffMillis / hourInMs);
	}
	
	/**
	 * example:
	 * 
	 * 2010.11.17-2010.11.17		1 day
	 * 2010.11.17-2010.11.18		2 days
	 */
	public static <T extends LocalDate<T>> long diffDaysInclusive(UserDate startDate, UserDate endDate) {
		Long diffMillis = diffMillis(startDate, endDate);
		return Math.round((double)diffMillis / dayInMs) + 1;
	}
	
	/**
	 * @param durationInMs in milliseconds
	 * @param showSec if true seconds is showed, if false the value of seconds will be rounded into minutes (0&lt;=seconds&lt;30 +0min, 30&lt;=seconds&lt;60 +1min)
	 * @return formatted duration, like 3h 34m
	 */
	public static String formatDuration(long durationInMs, boolean showSec){
		if(!showSec) {
			long secAndMs = durationInMs%minuteInMs;
			if(secAndMs >= 30*secondInMs) {
				durationInMs=durationInMs-secAndMs+minuteInMs;
			}
		}
			
		
		int days = (int) (durationInMs / dayInMs);
		durationInMs -= days * dayInMs;
		
		int hours = (int) (durationInMs / hourInMs);
		durationInMs -= hours * hourInMs;
		
		int minutes = (int) (durationInMs / minuteInMs);
		durationInMs -= minutes * minuteInMs;
		
		int seconds = (int) (durationInMs / secondInMs);
		
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
	
	/**
	 * Converts the durationInMs to human readable, nice format (see below)
	 * 
	 * @param durationInMs in milliseconds
	 * @param showTilde if true the returned string begins with '~'
	 * 
	 * @return on the current language: '10 secs ago' .. '30 secs ago', '1 min ago' .. '5 mins ago', '10 mins ago'... '50 mins ago', '1 hour ago' ...
	 */
	public static String approachDuration(long durationInMs, boolean showTilde, boolean shorten) {
		
		if(shorten)
			return approachDurationShort(durationInMs, showTilde);
		
		StringBuilder sb = new StringBuilder();
		long number=0;
		
		if(showTilde)
			sb.append("~");
		
		if(durationInMs<secondInMs*55) {
			number=10*divAndRoundToAvoidNull(10*secondInMs, durationInMs);
			sb.append(IneFrameI18n.secSAgo(number+""));
			return sb.toString();
		}
		
		if(durationInMs<minuteInMs*17) {
			number=divAndRoundToAvoidNull(minuteInMs, durationInMs);
			if(number==1)
				sb.append(IneFrameI18n.oneMinAgo());
			else
				sb.append(IneFrameI18n.minsAgo(number+""));
			return sb.toString();
		}
		
		
		if(durationInMs<hourInMs*2) {
			number=10*divAndRoundToAvoidNull(10*minuteInMs, durationInMs);
			if(number==1)
				sb.append(IneFrameI18n.oneMinAgo());
			else
				sb.append(IneFrameI18n.minsAgo(number+""));
			return sb.toString();
		}
		
		if(durationInMs<hourInMs*47) {
			number=divAndRoundToAvoidNull(hourInMs, durationInMs);
			if(number==1)
				sb.append(IneFrameI18n.oneHourAgo());
			else
				sb.append(IneFrameI18n.hoursAgo(number+""));
			return sb.toString();
		}
		
		number=divAndRoundToAvoidNull(dayInMs, durationInMs);
		if(number==1)
			sb.append(IneFrameI18n.oneDayAgo());
		else
			sb.append(IneFrameI18n.daysAgo(number+""));
		return sb.toString();
	}
	
	/**
	 * Does the same as the {@linkplain DateHelper.approachDuration} method, but the returned strings are shorter
	 * 
	 * @param durationInMs in milliseconds
	 * @param showTilde if true the returned string begins with '~'
	 * @return '~2h', '~10m', '2d', etc.
	 */
	private static String approachDurationShort(long durationInMs,	boolean showTilde) {
		
		StringBuilder sb = new StringBuilder();
		
		if(showTilde)
			sb.append("~");
		
		if(durationInMs<secondInMs*55) {
			sb.append(10*divAndRoundToAvoidNull(10*secondInMs, durationInMs));
			sb.append(IneFrameI18n.secShort());
			return sb.toString();
		}
		
		if(durationInMs<minuteInMs*17) {
			sb.append(divAndRoundToAvoidNull(minuteInMs, durationInMs));
			sb.append(IneFrameI18n.minShort());
			return sb.toString();
		}
		
		
		if(durationInMs<hourInMs*2) {
			sb.append(10*divAndRoundToAvoidNull(10*minuteInMs, durationInMs));
			sb.append(IneFrameI18n.minShort());
			return sb.toString();
		}
		
		if(durationInMs<hourInMs*47) {
			sb.append(divAndRoundToAvoidNull(hourInMs, durationInMs));
			sb.append(IneFrameI18n.hourShort());
			return sb.toString();
		}
		
		sb.append(divAndRoundToAvoidNull(dayInMs, durationInMs));
		sb.append(IneFrameI18n.dayShort());
		return sb.toString();
	}

	protected static long divAndRoundToAvoidNull(long magnitude, long num) {
		return Math.max(1, (num+magnitude/2)/magnitude);
	}
}