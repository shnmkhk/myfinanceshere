package org.rabbit.common;

import org.apache.commons.lang.StringUtils;

public class EntryCategory {

	public static final String	DEFAULT_CATEGORY	= "Other/ Miscelaneous";
	private String				label;

	public String getLabel() {
		return label;
	}

	private EntryCategory(String label) {
		this.label = label;
	}

	public static EntryCategory getCategory(String label) {
		if (StringUtils.isEmpty(label)) {
			return new EntryCategory(DEFAULT_CATEGORY);
		}
		return new EntryCategory(label);
	}
}