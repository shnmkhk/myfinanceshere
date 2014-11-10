package org.rabbit.shared;

import java.util.Map;

public class ValidationUtils {
	public static boolean doesAnyEntryHasCompleteData(int paramInt, Map<String, String> paramMap) {
		boolean doesAnyEntryHasCompleteData = false;
		for (int j = 0; j < paramInt; ++j) {
			String str = String.valueOf(paramMap.get("shortCode_" + j));
			double d = ObjectUtils.getDoubleValue(paramMap.get("amount_" + j), -1.0D);
			doesAnyEntryHasCompleteData = ObjectUtils.isNotNullAndNotEmpty(str) && (d != -1.0D);
			if (doesAnyEntryHasCompleteData) {
				break;
			}
		}
		return doesAnyEntryHasCompleteData;
	}
}