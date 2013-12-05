package org.rabbit.shared;

public class NumUtil {

	
	public static final int MINUS_ONE = -1;
	public static final int MONTH_LOWER_LIMIT = 1;
	public static final int MONTH_UPPER_LIMIT = 12;
	public static final int YEAR_LOWER_LIMIT = 1970;
	public static final int YEAR_UPPER_LIMIT = 9999;
	
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
