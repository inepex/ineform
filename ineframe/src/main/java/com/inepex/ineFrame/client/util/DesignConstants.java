package com.inepex.ineFrame.client.util;

public class DesignConstants {
	
	private static int base = 48;
	
	private static double b0d125 = 1.0/8.0;
	private static double b0d166 = 1.0/6.0;
	private static double b0d25 = 1.0/4.0;
	private static double b0d333 = 1.0/3.0;	
	private static double b0d315 = 0.315;
	private static double b0d27 = 13.0/48.0;
	private static double b0d291 = 14.0/48.0;
	
	private static double b0d5 = 1.0/2.0;	
	private static double b0d666 = 2.0/3.0;	
	private static double b0d75 = 3.0/4.0;
	private static double b1d08 = 52.0/48.0;
	private static double b1d5 = 1.5;	
	private static double b5 = 5;
	private static double b4 = 4;
	
	private static String userProfileBackgroundColor = "#303233";
	private static String backgroundColor = "#303233";
	private static String navigationDrawerBackgroundColor = "#3C3F40";
	private static String ineColor1 = "#12AD2B";  //"ine green
	private static String ineColor2 = "#A80000"; //"ine red"
	private static String ineColor3 = "#33ccff"; //"ine blue"
	private static String menuSeparatorColor = "#484C4D";
	private static String menuFontColor = "#BFC9CC";
	private static float defaultAnimationLength = 0.5f;
	
	public static final String appMenuIconId = "appMenuIcon";
	
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
	
	public static int b0d291() {
		return getRelativeProperty(b0d291);
	}
	public static String b0d291WithUnit() {
		return b0d291()+"px";
	}
	
	public static int b0d27() {
		return getRelativeProperty(b0d27);
	}
	public static String b0d27WithUnit() {
		return b0d27()+"px";
	}

	public static int b0d25() {
		return getRelativeProperty(b0d25);
	}
	public static String b0d25WithUnit() {
		return b0d25()+"px";
	}
	
	public static int b0d5() {
		return getRelativeProperty(b0d5);
	}
	public static String b0d5WithUnit() {
		return b0d5()+"px";
	}

	public static int b0d166() {
		return getRelativeProperty(b0d166);
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
	
	public static int b1d08() {
		return getRelativeProperty(b1d08);
	}
	public static String b1d08WithUnit() {
		return b1d08()+"px";
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
		return -1 * b0d5();
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
	
	public static String getUserProfileBackgroundColor() {
		return userProfileBackgroundColor;
	}

	public static String getIneColor1() {
		return ineColor1;
	}
	
	public static String getIneColor2() {
		return ineColor2;
	}
	
	public static String getIneColor3() {
		return ineColor3;
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
	
	public static void setIneColor1(String color){
		ineColor1 = color;
	}
	
	public static void setIneColor2(String color){
		ineColor2 = color;
	}
	
	public static void setIneColor3(String color){
		ineColor3 = color;
	}
	public static String getNavigationDrawerBackgroundColor() {
		return navigationDrawerBackgroundColor;
	}
}
