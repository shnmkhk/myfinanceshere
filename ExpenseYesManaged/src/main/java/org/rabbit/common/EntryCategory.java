package org.rabbit.common;

import org.rabbit.shared.TextUtil;

public enum EntryCategory {

	SALARY("Salary"), FOOD("Food"), TRANSPORT("Transport"), CLOTHING("Clothing"), ENTERTAINMENT(
			"Entertainment"), HOUSE_RENT("House Rent"), HOUSEHOLD_GOODS(
			"Household Goods"), GROCERIES("Groceries"), TRAVEL("Travel"), ELECTRICITY(
			"Electricity"), INTERNET("Internet"), MOBILE_PHONE_RECHARGES_AND_BILLS(
			"Mobile Phone Recharges/ Bills"), FACILITIES("Facilities"), HOUSE_RENOVATION(
			"House Renovation"), OTHERS_MISCELANEOUS ("Others/ Miscelaneous");

	private String label;
	
	public String getLabel() {
		return label;
	}

	private EntryCategory(String label) {
		this.label = label;
	}
	
	public static EntryCategory getCategory (String label) {
		if (TextUtil.getStringValue(label).length() == 0) {
			return EntryCategory.OTHERS_MISCELANEOUS;
		}
		for (EntryCategory category: values()) {
			if  (label.equals(category.label)) {
				return category;
			}
		}
		return EntryCategory.OTHERS_MISCELANEOUS;
	}
}