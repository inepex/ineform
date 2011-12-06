package com.inepex.ineForm.client.form.widgets.datetime;

import com.google.gwt.user.datepicker.client.CalendarModel;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.google.gwt.user.datepicker.client.DefaultCalendarView;
import com.google.gwt.user.datepicker.client.DefaultMonthSelector;
import com.google.gwt.user.datepicker.client.MonthSelector;
import com.inepex.ineForm.client.i18n.DateI18nHelper;

public class I18nDatePicker extends DatePicker {
	
	public I18nDatePicker() {
		super(new DefaultMonthSelector(), new DefaultCalendarView(), new CustomFormatCalendarModel());
	}
	
	public MonthSelector getMonthSelectorPublic(){
		return getMonthSelector();
	}
	
	public static class CustomFormatCalendarModel extends CalendarModel {
		
		@Override
		public String formatDayOfWeek(int dayInWeek) {
			return DateI18nHelper.formatDayOfWeek(dayInWeek);
		}
		
		@Override
		@SuppressWarnings("deprecation")
		public String formatCurrentMonth() {
			return (getCurrentMonth().getYear()+1900)
					+". "
					+formatMonth(getCurrentMonth().getMonth());
		}
		
		public String formatMonth(int month) {
			return DateI18nHelper.formatMonth(month);			
		}
	}
	
}