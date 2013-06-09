package org.rabbit.shared;

public class NumUtil {

	
	public static int getIntValue(Object intValueStr, int defaultValue){
		if (intValueStr == null) {
			return defaultValue;
		}
		try {
			return Integer.parseInt(String.valueOf(intValueStr));
		} catch (NumberFormatException nfe){
			return defaultValue;
		}
	}

	public static double getDoubleValue(String doubleValueStr, double defaultValue){
		if (doubleValueStr == null) {
			return defaultValue;
		}
		try {
			return Double.parseDouble(doubleValueStr);
		} catch (NumberFormatException nfe){
			return defaultValue;
		}
	}

}
