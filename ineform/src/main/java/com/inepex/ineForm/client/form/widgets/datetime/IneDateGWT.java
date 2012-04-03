package com.inepex.ineForm.client.form.widgets.datetime;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.inepex.ineForm.client.IneFormProperties;

/*
 * A fieldek sokszinűsége miatt, meg egyébként is ez a nulldate lehet, hogy halott ötlet!
 * 
 * Talán érdemesebb az isEmpty és az isNull irányzatot követni, minthogy,
 * a field javaslatot, tesz, hogy a textboxok legyenek-e üresek és hogy a FW értéke legyen-e null. Nem gondoltam át, de talán
 * így könnyebben kezelhetőek lesznek a cuccok!
 * 
 * sebi (2011.03.21)
 * 
 */

/**
 * 
 * showing, parsing with browsers time zone settings
 * 
 */
public class IneDateGWT {

	/**
	 * The precision of the used date.
	 *
	 */
	public enum Precision {

		/**
		 * like: 2008.03.21 13:32
		 */
		YMD_HM(IneFormProperties.YMD_HM),
		
		/**
		 * like: 13:32
		 */
		OOO_HM(IneFormProperties.OOO_HM),
		
		/**
		 * like: 13 (13 hour )
		 */
		OOO_HO(IneFormProperties.OOO_HO),
		
		/**
		 * like: 32 (32 minutes )
		 */
		OOO_OM(IneFormProperties.OOO_OM),
		
		/**
		 * like: 13:32:12
		 */
		OOO_HMS(IneFormProperties.OOO_HMS),

		/**
		 * like: 2008.06.11
		 */
		YMD_OO(IneFormProperties.YMD_OO),

		/**
		 * like: 2008.06
		 */
		YMO_OO(IneFormProperties.YMO_OO),

		/**
		 * like: 2008
		 */
		YOO_OO(IneFormProperties.YOO_OO),

		/**
		 * like: 03
		 */
		OMO_OO(IneFormProperties.OMO_OO);

		private String formatstring;
		
		private Precision(String formatstring) {
			this.formatstring=formatstring;
		}
		
		public DateTimeFormat getFormatter() {
			return DateTimeFormat.getFormat(formatstring);
		}
		
		public String getFormattterString() {
			return formatstring;
		}

	}

	private final Date date;

	//values
	public final static long SEC=1000L;
	public final static long MIN=SEC*60L;
	public final static long HOUR=MIN*60L;
	public final static long DAY=HOUR*24L;
	
	public static final long NULLDATE = -62135769600000L;

	public IneDateGWT() {
		date=new Date(NULLDATE);
	}

	/**
	 * to display IneDate in a Label...
	 * @param precisionfortext
	 * @return
	 */
	public String getText(Precision precisionfortext) {
		return precisionfortext.getFormatter().format(date);
	}

	/**
	 * to decide what textbox component contains date information
	 *
	 * @param precision
	 * @return
	 */
	public boolean isEmpty(Precision precision) {
		switch(precision) {
			case YOO_OO: return "1".equals(precision.getFormatter().format(date));
			case YMO_OO: return "1.1".equals(precision.getFormatter().format(date));
			
			//FIXME
			case YMD_OO: return "0001.01.01".equals(precision.getFormatter().format(date)) ||
									"0000.12.30".equals(precision.getFormatter().format(date)); //don't know what happends in compiled code
			case YMD_HM: return CalendarUtil.isSameDate(date, new Date(NULLDATE));

			case OMO_OO:
			case OOO_HM:
			case OOO_HO:
			case OOO_OM:
			case OOO_HMS: return false;
		}
		
		throw new RuntimeException("IneDateGWT: isEmpty(): there is no case for: "+precision.toString());
	}

	@SuppressWarnings("deprecation")
	public void setDate(Precision prec, Date value) {
		switch (prec) {
			case YMD_HM:
				date.setTime(value.getTime());
				return;
	
			case OOO_HM:
				date.setHours(value.getHours());
				date.setMinutes(value.getMinutes());
				return;
				
			case OOO_HO:
				date.setHours(value.getHours());
				return;
				
			case OOO_OM:
				date.setMinutes(value.getMinutes());
				return;
				
			case OOO_HMS:
				date.setHours(value.getHours());
				date.setMinutes(value.getMinutes());
				date.setSeconds(value.getSeconds());
				return;
	
			case YMD_OO:
				int h=date.getHours();
				int m=date.getMinutes();
				int s=date.getSeconds();
				date.setTime(value.getTime());
				date.setHours(h);
				date.setMinutes(m);
				date.setSeconds(s);
				return;
	
			case YMO_OO:
				if(date.getYear()==-1899) {
					date.setYear(value.getYear());
					date.setMonth(value.getMonth());
					return;
				}
				
				CalendarUtil.setToFirstDayOfMonth(value);
				Date tmpdate=CalendarUtil.copyDate(date);
				CalendarUtil.setToFirstDayOfMonth(tmpdate);
	
				int i=0;
				if(tmpdate.getTime()<value.getTime()) {
					while(!CalendarUtil.isSameDate(value, tmpdate)) {
						CalendarUtil.addMonthsToDate(tmpdate, 1);
						i++;
					}
				} else {
					while(!CalendarUtil.isSameDate(value, tmpdate)) {
						CalendarUtil.addMonthsToDate(tmpdate, -1);
						i--;
					}
				}
	
				addMonthsToDateSafe(date, i);
				return;
	
			case YOO_OO:
				if(date.getYear()==-1899) {
					date.setYear(value.getYear());
					return;
				}
				
				CalendarUtil.setToFirstDayOfMonth(value);
				value.setMonth(0);
	
				tmpdate = CalendarUtil.copyDate(date);
				CalendarUtil.setToFirstDayOfMonth(tmpdate);
				tmpdate.setMonth(0);
	
				if(tmpdate.getTime()<value.getTime()) {
					while(!CalendarUtil.isSameDate(value, tmpdate)) {
						CalendarUtil.addMonthsToDate(tmpdate, 12);
						CalendarUtil.addMonthsToDate(date, 12);
					}
				} else {
					while(!CalendarUtil.isSameDate(value, tmpdate)) {
						CalendarUtil.addMonthsToDate(tmpdate, -12);
						CalendarUtil.addMonthsToDate(date, -12);
					}
				}
				return;
	
			case OMO_OO:
				int yt = date.getYear();
				addMonthsToDateSafe(date, value.getMonth()-date.getMonth()+1);
				date.setYear(yt);
				
				return;
		}
		
		throw new RuntimeException("IneDateGWT: setDate(): there is no case for: "+prec.toString());
	}

	@SuppressWarnings("deprecation")
	public void setDateNull(Precision prec) {
		if(prec==Precision.YOO_OO) {
			date.setYear(-1899);
			return;
		}
		if(prec==Precision.YMO_OO) {
			date.setYear(-1899);
			date.setMonth(0);
			return;
		}
		setDate(prec, new Date(NULLDATE));
	}

	/**
	 * 
	 * @param prec
	 * @param count if negative, it is a "step backwards"
	 */
	public void stepForward(Precision prec, int count) {
		switch (prec) {
			case YMD_HM:
				date.setTime(date.getTime()+MIN*count);
				return;
	
			case OOO_OM:
			case OOO_HM:
				date.setTime(date.getTime()+MIN*count);
				return;
				
			case OOO_HO:
				date.setTime(date.getTime()+MIN*60*count);
				return;
				
			case OOO_HMS:
				date.setTime(date.getTime()+SEC*count);
				return;
				
			case YMD_OO:
				CalendarUtil.addDaysToDate(date, count);
				return;
	
			case YMO_OO:
				addMonthsToDateSafe(date, count);
				return;
	
			case YOO_OO:
				CalendarUtil.addMonthsToDate(date, 12*count);
				return;
				
			case OMO_OO:
				return;
		}
		
		throw new RuntimeException("IneDateGWT: stepOneForward(): there is no case for: "+prec.toString());
	}
	
	/**
	 * it's behavior is like Calendar's addmonth(), but it works fine
	 * @param date
	 * @param delta
	 */
	@SuppressWarnings("deprecation")
	public static void addMonthsToDateSafe(Date date, int delta) {
		int h=date.getHours();
		int m=date.getMinutes();
		int s=date.getSeconds();
		int d=date.getDate()-1;

		CalendarUtil.setToFirstDayOfMonth(date);
		CalendarUtil.addMonthsToDate(date, delta);

		int month=date.getMonth();

		while(d>0 && date.getMonth()==month) {
			CalendarUtil.addDaysToDate(date, 1);
			d--;
		}

		if(date.getMonth()!=month) {
			CalendarUtil.addDaysToDate(date, -1);
		}
		
		date.setHours(h);
		date.setMinutes(m);
		date.setSeconds(s);
	}

	public void initLongValue(long dateinlong) {
		date.setTime(dateinlong);
		
	}

	public Long getLongValue() {
		return new Long(date.getTime());
	}


	public Date getDateClone() {
		return CalendarUtil.copyDate(date);
	}

	public void setToNowRoundToSec(int rt) {
		date.setTime(((new Date().getTime()+rt*500L)/(rt*1000L))*(rt*1000L));
	}

	public void setToNow() {
		date.setTime(new Date().getTime());
	}

}

