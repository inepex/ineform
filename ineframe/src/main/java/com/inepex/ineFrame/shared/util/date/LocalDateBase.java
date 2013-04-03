package com.inepex.ineFrame.shared.util.date;

import java.util.Date;


/**
 * Base class of ineform dating. Holds methods what are safety usable without time zone information.
 * 
 * @author sebi
 *
 */
public abstract class LocalDateBase {
	
	private long time;
	
	protected LocalDateBase(long time) {
		this.time=time;
	}
	
	protected LocalDateBase(Date d) {
		this.time=d.getTime();
	}

	protected long getTime() {
		return time;
	}
	
	protected void setTime(long time) {
		this.time = time;
	}
	
	public void resetMinSecAndMillis() {
		time=(time/DateHelper.hourInMs)*DateHelper.hourInMs;
	}
	
	public void addHours(int hours) {
		addHours((long)hours);
	}

	public void addHours(long hours) {
		time += hours * DateHelper.hourInMs;
	}

	public void addMinutes(int minutes) {
		addMinutes((long)minutes);
	}

	public void addMinutes(long minutes) {
		time += minutes * DateHelper.minuteInMs;
	}

	public void addSeconds(int seconds) {
		addSeconds((long)seconds);
	}

	public void addSeconds(long seconds) {
		time+= seconds * DateHelper.secondInMs;
	}
}
