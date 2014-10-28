package com.inepex.ineFrame.client.util;

public class ProportionConstants {
	
	public static int base = 48;
	
	public static int b0d333 = (int) (base * 1.0/3.0);
	public static int b0d25 = (int) (base * 1.0/4.0);
	public static int b0d166 = (int) (base * 1.0/6.0);
	public static int b0d125 = (int) (base * 1.0/8.0);
	
	public static int b0d666 = (int) (base * 2.0/3.0);
	
	public static int b1d5 = (int) (base * 1.5);
	
	public static int b5 = (int) (base * 5);

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
}
