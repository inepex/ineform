package com.inepex.ineFrame.shared.util.date;

import java.util.Date;


/**
 * 
 * Local time zone dependent calc methods.
 * 
 * @author sebi
 *
 */
public abstract class LocalDate<T extends LocalDate<T>> extends LocalDateBase {
	
	protected LocalDate(long time) {
		super(time);
	}
	
	protected LocalDate(Date d) {
		super(d);
	}
	
	protected abstract T newInstance(long time);
	
	private Date date() {
		return new Date(getTime());
	}
	
	public int getCalendarStyleYear() {
		return getYear() + 1900;
	}
	
	public T createCopy() {
		return newInstance(getTime());
	}
	
	@SuppressWarnings("deprecation")
	int getYear() {
		return date().getYear() ;
	}
	
	public int getCalendarStyleMonth() {
		return getMonth();
	}
	
	@SuppressWarnings("deprecation")
	int getMonth() {
		return date().getMonth();
	}
	
	public int getCalendarStyleDate() {
		return getDate();
	}
	
	@SuppressWarnings("deprecation")
	int getDate() {
		return date().getDate();
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public T setToFirstMSOfDay() {
		resetMinSecAndMillis();
		Date d = date();
		d.setHours(0);
		setTime(d.getTime());
		return (T) this;
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public T setToNoon() {
		resetMinSecAndMillis();
		Date d = date();
		d.setHours(12);
		setTime(d.getTime());
		return (T) this;
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public T setToLastMSOfDay() {
		resetMinSecAndMillis();
		Date d = date();
		d.setHours(23);
		d.setMinutes(59);
		d.setSeconds(59);
		setTime(d.getTime()+DateHelper.secondInMs-1);
		return (T) this;
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public T addDaysSafe(int days) {
		Date d = date();
		// the Date class handles it correctly even if the month doesn't have that many days as 
		// it is set in the argument (it adjusts the year, month and day if necessary)
		d.setDate(d.getDate() + days); 
		setTime(d.getTime());
		return (T) this;
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public T setToNextWeekend(){
		addDaysSafe(1);
		while (date().getDay() != 6){
			addDaysSafe(1);
		}
		
		return (T) this;
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public T setToWeekStart(){
		while (date().getDay() != 1){
			addDaysSafe(-1);
		}
		
		return (T) this;
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public T setToMonthStart(){
		Date date = new Date(getTime());
		date.setDate(1);
		setTime(date.getTime());
		return (T) this;
	}
	
	@SuppressWarnings("unchecked")
	public T setToMonthEnd(){
		int month = getMonth();
		do {
			addDaysSafe(1);
		} while (getMonth() == month);
		addDaysSafe(-1);
		
		return (T) this;
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public T setToNextQuarterStart(){
		int nextQuarter = getQuarter() + 1;
		int year = getYear();
		if (nextQuarter == 5) {
			nextQuarter = 1;
			year++;
		}
		switch (nextQuarter){
		case 1: 
			setTime(new Date(year, 0, 1).getTime());
			break;
		case 2: 
			setTime(new Date(year, 3, 1).getTime());
			break;
		case 3: 
			setTime(new Date(year, 6, 1).getTime());
			break;
		case 4: 
			setTime(new Date(year, 9, 1).getTime());
			break;
		default: 
			throw new IllegalStateException();
		}
		
		return (T) this;
	}
	
	public int getNextQuarter(){
		int nextQuarter = getQuarter() + 1;
		if (nextQuarter == 5) {
			nextQuarter = 1;
		}
		return nextQuarter;
	}
	
	public int getQuarter(){
		switch (getMonth()){
			case 0: 
			case 1: 
			case 2: 
				return 1;
			case 3: 
			case 4: 
			case 5: 
				return 2;
			case 6: 
			case 7: 
			case 8: 
				return 3;
			case 9: 
			case 10: 
			case 11: 
				return 4;
			default: 
				throw new IllegalStateException();
		}
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public T setToNextHalfYearStart(){
		int nextHalf = getHalfYear() + 1;
		int year = getYear();
		if (nextHalf == 3) {
			nextHalf = 1;
			year++;
		}
		switch (nextHalf){
		case 1: 
			setTime(new Date(year, 0, 1).getTime());
			break;
		case 2: 
			setTime(new Date(year, 6, 1).getTime());
			break;
		default: 
			throw new IllegalStateException();
		}
		
		return (T) this;
	}
	
	public int getHalfYear(){
		if (getMonth() <=5) return 1;
		else return 2;
	}
	
	/**
	 * Adds month to the current date. 
	 * If the {@link LocalDate#isLastDayOfMonth(Date)} is true before adding, it will be true after it. For example {@code <Apr 30>+1 month will be <May 31>} 
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	public T addMonthAbstract(int months) {
		Date tmpDate = new Date(getTime());
		boolean endOfMonth=isLastDayOfMonth(tmpDate);
		
		tmpDate.setMonth(getMonth() + months);
		
		int monthsOfYearDiff = (tmpDate.getYear() - getYear()) * 12;
		while ((tmpDate.getMonth() + monthsOfYearDiff) != getMonth() + months){
			tmpDate.setDate(tmpDate.getDate() - 1);
		}
		
		if(endOfMonth) {
			while(!isLastDayOfMonth(tmpDate))
				tmpDate.setDate(tmpDate.getDate()+1);
		}
		
		setTime(tmpDate.getTime());
		return (T) this;
	}

	@SuppressWarnings("deprecation" )
	public boolean isLastDayOfMonth(Date date) {
		Date tmpDate = new Date(date.getTime());
		tmpDate.setDate(tmpDate.getDate()+1);
		
		return tmpDate.getMonth()!=date.getMonth();
	}

	
	@SuppressWarnings("deprecation")
	public boolean isToday() {
		Date today = new Date();
		return today.getYear()==getYear() 
				&& today.getMonth() == getMonth() 
				&& today.getDate()==getDate();
	}
}