package com.inepex.ineForm.client.i18n;


public class DateI18nHelper {

	public static String formatDayOfWeek(int dayInWeek) {
		switch (dayInWeek) {
		case 0:
			return IneFormI18n.shortday_sunday();
		case 1:
			return IneFormI18n.shortday_monday();
		case 2:
			return IneFormI18n.shortday_tuesday();
		case 3:
			return IneFormI18n.shortday_wednesday();
		case 4:
			return IneFormI18n.shortday_thursday();
		case 5:
			return IneFormI18n.shortday_friday();
		case 6:
			return IneFormI18n.shortday_saturday();
		default:
			return "";
		}
	}
		
	public static String formatMonth(int month) {
		switch (month) {
		case 0: 
			return IneFormI18n.month_january();
		case 1: 
			return IneFormI18n.month_february();
		case 2: 
			return IneFormI18n.month_march();
		case 3: 
			return IneFormI18n.month_april();
		case 4: 
			return IneFormI18n.month_may();
		case 5: 
			return IneFormI18n.month_june();
		case 6: 
			return IneFormI18n.month_july();
		case 7: 
			return IneFormI18n.month_august();
		case 8: 
			return IneFormI18n.month_september();
		case 9: 
			return IneFormI18n.month_october();
		case 10: 
			return IneFormI18n.month_november();
		case 11: 
			return IneFormI18n.month_december();
		default:
			return "";
		}
		
	}
}
