package org.rabbit.shared;

public class NumUtil {

	
	public static int getIntValue(String intValueStr, int defaultValue){
		try {
			return Integer.parseInt(intValueStr);
		} catch (NumberFormatException nfe){
			return defaultValue;
		}
	}

	public static double getDoubleValue(String doubleValueStr, double defaultValue){
		try {
			return Double.parseDouble(doubleValueStr);
		} catch (NumberFormatException nfe){
			return defaultValue;
		}
	}

}
