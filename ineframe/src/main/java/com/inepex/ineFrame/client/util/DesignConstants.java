package com.inepex.ineFrame.client.util;

public class DesignConstants {
	
	private static int base = 48;
	
	private static double b0d333 = 1.0/3.0;
	
	private static double b0d315 = 12.0/48.0;
	
	private static double b0d25 = 1.0/4.0;
	private static double b0d50 = 1.0/2.0;
	private static double b0d166 = 1.0/6.0;
	private static double b0d125 = 1.0/8.0;
	
	private static double b0d666 = 2.0/3.0;
	
	private static double b0d75 = 3.0/4.0;
	
	private static double b1d5 = 1.5;
	
	private static double b5 = 5;
	private static double b4 = 4;
	
	private static String backgroundColor = "#2B2B2B";
	private static String ineColor1 = "#12AD2B";
	private static String ineColor2 = "#A80000";
	private static String menuSeparatorColor = "#494949";
	private static String menuFontColor = "#CACACA";
	private static float defaultAnimationLength = 0.5f;
	
	public static int base() {
		return base;
	}
	public static String baseWithUnit() {
		return base()+"px";
	}
	
	public static int b0d315() {
		return getRelativeProperty(b0d315);
	}
	public static String b0d315WithUnit() {
		return b0d315()+"px";
	}

	public static int b0d333() {
		return getRelativeProperty(b0d333);
	}
	public static String b0d333WithUnit() {
		return b0d333()+"px";
	}

	public static int b0d25() {
		return getRelativeProperty(b0d25);
	}
	public static String b0d25WithUnit() {
		return b0d25()+"px";
	}
	
	public static int b0d50() {
		return getRelativeProperty(b0d50);
	}
	public static String b0d50WithUnit() {
		return b0d50()+"px";
	}

	public static int b0d166() {
		return getRelativeProperty(b0d50);
	}
	public static String b0d166WithUnit() {
		return b0d166()+"px";
	}

	public static int b0d125() {
		return getRelativeProperty(b0d125);
	}
	public static String b0d125WithUnit() {
		return b0d125()+"px";
	}

	public static int b0d666() {
		return getRelativeProperty(b0d666);
	}
	public static String b0d666WithUnit() {
		return b0d666()+"px";
	}
	
	public static int b0d75() {
		return getRelativeProperty(b0d75);
	}
	public static String b0d75WithUnit() {
		return b0d75()+"px";
	}

	public static int b1d5() {
		return getRelativeProperty(b1d5);
	}
	public static String b1d5WithUnit() {
		return b1d5()+"px";
	}

	public static int b5() {
		return getRelativeProperty(b5);
	}
	public static String b5WithUnit() {
		return b5()+"px";
	}
	
	public static int b4() {
		return getRelativeProperty(b4);
	}
	public static String b4WithUnit() {
		return b4()+"px";
	}	
	
	public static int baseNegative() {
		return -1 * base;
	}
	public static String baseNegativeWithUnit() {
		return (-1 * base())+"px";
	}
	
	public static int b0d333Negative() {
		return -1 * b0d333();
	}
	public static String b0d333NegativeWithUnit() {
		return (-1 * b0d333())+"px";
	}

	public static int b0d25Negative() {
		return -1 * b0d25();
	}
	public static String b0d25NegativeWithUnit() {
		return (-1 * b0d25())+"px";
	}
	
	public static int b0d50Negative() {
		return -1 * b0d50();
	}
	public static String b0d50NegativeWithUnit() {
		return (-1 * base())+"px";
	}

	public static int b0d166Negative() {
		return -1 * b0d166();
	}
	public static String b0d166NegativeWithUnit() {
		return (-1 * b0d166())+"px";
	}

	public static int b0d125Negative() {
		return -1 * b0d125();
	}
	public static String b0d125NegativeWithUnit() {
		return (-1 * b0d125())+"px";
	}

	public static int b0d666Negative() {
		return -1 * b0d666();
	}
	public static String b0d666NegativeWithUnit() {
		return (-1 * b0d666())+"px";
	}

	public static int b1d5Negative() {
		return -1 * b1d5();
	}
	public static String b1d5NegativeWithUnit() {
		return (-1 * b1d5())+"px";
	}

	public static int b5Negative() {
		return -1 * b5();
	}
	public static String b5NegativeWithUnit() {
		return (-1 * b5())+"px";
	}
	
	public static String getBackgroundColor() {
		return backgroundColor;
	}

	public static String getIneColor1() {
		return ineColor1;
	}
	
	public static String getIneColor2() {
		return ineColor2;
	}

	public static String getMenuSeparatorColor() {
		return menuSeparatorColor;
	}

	public static String getMenuFontColor() {
		return menuFontColor;
	}
	public static float defaultAnimationLength() {
		return defaultAnimationLength;
	}
	public static String defaultAnimationLengthWithUnit() {
		return defaultAnimationLength + "s";
	}
	
	public static void setBase(int base) {
		DesignConstants.base = base;
	}
	public static int getRelativeProperty(double value){
		return (int)(base()*value);
	}
	public static String getRelativePropertyWithUnit(double value,String unit){
		return getRelativeProperty(value)+unit;
	}
}
