package org.rabbit.shared;

import com.google.appengine.api.datastore.Key;

public class TextUtil {

	public static final String getNumbersAlone(String input){
		final String REPLACE_STRING = "[^\\s0-9\\-]|((?<!\\S*\\d)-(?!\\S*\\d))";
		return input.replaceAll(REPLACE_STRING, "");
		
	}
	
	
	public static final String getSheetKeyId(int month, int year){
		return String.valueOf(month) + "_" + String.valueOf(year);
	}
	
	public static final String getEntryKeyId(Key parentKey, int sequenceIndex){
		return parentKey.getName() + "_" + sequenceIndex;
	}
	
	public static void main(String[] args) {
		System.out.println(getNumbersAlone("abcd1234efgh~!@#$%^&*()_+\\|=-{}[];;\'\"<,.>/?"));
	}

}
