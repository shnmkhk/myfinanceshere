package org.rabbit.shared;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.appengine.api.datastore.Key;

public class TextUtil {

	protected static final int			MAXIMUM_ALLOWED_WORD_LENGTH	= 18;
	public static final NumberFormat	nf							= new DecimalFormat("###,###,###.00");

	public static final String getNumbersAlone(String input) {
		final String REPLACE_STRING = "[^\\s0-9\\-]|((?<!\\S*\\d)-(?!\\S*\\d))";
		return input.replaceAll(REPLACE_STRING, "");

	}

	public static final String getSheetKeyId(int month, int year) {
		return String.valueOf(month) + "_" + String.valueOf(year);
	}

	public static final String getSheetKeyId(String month, String year) {
		return String.valueOf(month) + "_" + String.valueOf(year);
	}

	public static final String getEntryKeyId(Key parentKey, int sequenceIndex) {
		return parentKey.getName() + "_" + sequenceIndex;
	}

	public static final String getStringValue(Object obj) {
		if (obj == null) {
			return "";
		}

		return obj.toString();
	}

	private static StringBuffer	sb	= new StringBuffer();

	private static String getCorrectedString(String s) {
		sb.setLength(0);
		while (s.length() > MAXIMUM_ALLOWED_WORD_LENGTH) {
			sb.append(s.substring(0, MAXIMUM_ALLOWED_WORD_LENGTH));
			s = s.substring(MAXIMUM_ALLOWED_WORD_LENGTH);
			sb.append(" ");
		}
		sb.append(s);
		return sb.toString();
	}

	public static final String formatStr(String commentDescription) {
		commentDescription = TextUtil.getStringValue(commentDescription);
		String[] splitComments = commentDescription.split("[ ,.;:]");
		Map<String, String> cache = new LinkedHashMap<String, String>();
		for (String splitComment : splitComments) {
			cache.put(splitComment, getCorrectedString(splitComment));
		}
		for (Map.Entry<String, String> commentEntry : cache.entrySet()) {
			commentDescription = commentDescription.replaceAll(commentEntry.getKey(), commentEntry.getValue());
		}
		return commentDescription;
	}

	public static void main(String[] args) {
		System.out.println(getNumbersAlone("abcd1234efgh~!@#$%^&*()_+\\|=-{}[];;\'\"<,.>/?"));
	}
}
