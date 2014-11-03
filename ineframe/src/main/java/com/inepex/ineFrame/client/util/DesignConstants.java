package com.inepex.ineFrame.client.util;

public class DesignConstants {
	
	public static int base = 48;
	
	public static int b0d333 = (int) (base * 1.0/3.0);
	public static int b0d25 = (int) (base * 1.0/4.0);
	public static int b0d166 = (int) (base * 1.0/6.0);
	public static int b0d125 = (int) (base * 1.0/8.0);
	
	public static int b0d666 = (int) (base * 2.0/3.0);
	
	public static int b1d5 = (int) (base * 1.5);
	
	public static int b5 = (int) (base * 5);
	
	public static String backgroundColor = "#2B2B2B";
	public static String ineColor1 = "#12AD2B";
	public static String menuSeparatorColor = "#494949";
	public static String menuFontColor = "#CACACA";
	public static float defaultAnimationLength = 0.5f;
	
	public static String base() {
		return base + "px";
	}

	public static String b0d333() {
		return b0d333+"px";
	}

	public static String b0d25() {
		return b0d25 + "px";
	}

	public static String b0d166() {
		return b0d166 + "px";
	}

	public static String b0d125() {
		return b0d125 + "px";
	}

	public static String b0d666() {
		return b0d666 + "px";
	}

	public static String b1d5() {
		return b1d5 + "px";
	}

	public static String b5() {
		return b5 + "px";
	}

	public static String getBackgroundColor() {
		return backgroundColor;
	}

	public static String getIneColor1() {
		return ineColor1;
	}

	public static String getMenuSeparatorColor() {
		return menuSeparatorColor;
	}

	public static String getMenuFontColor() {
		return menuFontColor;
	}
	
	public static String baseNegative() {
		return -1 * base + "px";
	}
	
	public static String defaultAnimationLength() {
		return defaultAnimationLength + "s";
	}
	
	public static void setBase(int base) {
		DesignConstants.base = base;
	}
}
