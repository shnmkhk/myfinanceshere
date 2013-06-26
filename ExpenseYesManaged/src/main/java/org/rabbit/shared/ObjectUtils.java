package org.rabbit.shared;

import com.google.appengine.api.datastore.Key;

public class ObjectUtils {

	public static final String getNumbersAlone(String input) {
		final String REPLACE_STRING = "[^\\s0-9\\-]|((?<!\\S*\\d)-(?!\\S*\\d))";
		return input.replaceAll(REPLACE_STRING, "");
	}

	public static final String getSheetKeyId(int month, int year) {
		return String.valueOf(month) + "_" + String.valueOf(year);
	}

	public static final String[] getMonthYr(String sheetKeyId) {
		if (isNullOrEmpty(sheetKeyId)) {
			return null;
		}
		return sheetKeyId.split("_");
	}

	public static final String getEntryKeyId(Key parentKey, int sequenceIndex) {
		if (isNullOrEmpty(parentKey)) {
			return null;
		}
		return parentKey.getName() + "_" + sequenceIndex;
	}

	public static void main(String[] args) {
		System.out
				.println(getNumbersAlone("abcd1234efgh~!@#$%^&*()_+\\|=-{}[];;\'\"<,.>/?"));
	}

	public static final boolean isNullOrEmpty(Object obj) {
		if (obj == null || obj.toString().length() == 0) {
			return true;
		}

		return false;
	}

	public static final boolean isNotNullAndNotEmpty(Object obj) {

		if (obj != null && obj instanceof String) {
			if (obj.toString().length() > 0) {
				return true;
			}
		} else if (obj != null) {
			return true;
		}

		return false;
	}

	public static final int getIntValue(Object obj, int defaultValue) {
		if (isNullOrEmpty(obj)) {
			return defaultValue;
		}

		try {
			return Integer.parseInt(obj.toString());
		} catch (NumberFormatException nfe) {
			return defaultValue;
		}
	}

	public static final long getLongValue(Object obj, long defaultValue) {
		if (isNullOrEmpty(obj)) {
			return defaultValue;
		}

		try {
			return Long.parseLong(obj.toString());
		} catch (NumberFormatException nfe) {
			return defaultValue;
		}
	}

	public static final double getDoubleValue(Object obj, double defaultValue) {
		if (isNullOrEmpty(obj)) {
			return defaultValue;
		}

		try {
			return Double.parseDouble(obj.toString());
		} catch (NumberFormatException nfe) {
			return defaultValue;
		}
	}

	public static final float getFloatValue(Object obj, float defaultValue) {
		if (isNullOrEmpty(obj)) {
			return defaultValue;
		}

		try {
			return Float.parseFloat(obj.toString());
		} catch (NumberFormatException nfe) {
			return defaultValue;
		}
	}

	public static final boolean getBooleanValue(Object obj, boolean defaultValue) {
		if (isNullOrEmpty(obj)) {
			return defaultValue;
		}

		try {
			return Boolean.parseBoolean(obj.toString());
		} catch (NumberFormatException nfe) {
			return defaultValue;
		}
	}

	public static final String getStrValue(Object obj) {
		if (isNotNullAndNotEmpty(obj)) {
			return obj.toString();
		}

		return RequestUtil.EMPTY_STR;
	}
}