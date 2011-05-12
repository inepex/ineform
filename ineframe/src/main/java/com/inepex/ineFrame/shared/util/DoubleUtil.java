package com.inepex.ineFrame.shared.util;

public class DoubleUtil {

	public static Double stubToMaxDigits(int maxdigits, Double number) {
		if(number==null)
			return null;
		
		int mul = number>=0 ? 1 : -1;
		double stubber = 1;
		for(int i=0; i<maxdigits; i++) {
			stubber*=10;
		}
		
		return mul * (Math.floor(Math.abs(number)*stubber)/stubber);
	}
	
}
