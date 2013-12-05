/**
 * 
 */
package org.rabbit.services.dwr.vo;

import java.io.Serializable;

import org.rabbit.shared.TextUtil;

/**
 * @author shanmukha.k@gmail.com <br/>
 *         for <b>Rabbit Computing, Inc.</b> <br/>
 * <br/>
 *         Date created: Aug 3, 2013
 */
public class SheetVO extends BaseAbstractVO implements Serializable {

	private static final long serialVersionUID = -4875450449226274227L;

	private String month;

	private String year;

	/**
	 * @return the month
	 */
	public String getMonth() {
		return month;
	}

	/**
	 * @param month
	 *            the month to set
	 */
	public void setMonth(String month) {
		this.month = month;
	}

	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param year
	 *            the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}

	public String getKeyStr() {
		return TextUtil.getSheetKeyId(month, year);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "SheetVO [month=" + month + ", year=" + year + "]";
	}
}