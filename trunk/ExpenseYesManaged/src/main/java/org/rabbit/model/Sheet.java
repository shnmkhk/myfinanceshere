package org.rabbit.model;

import java.io.Serializable;

import javax.jdo.annotations.PersistenceCapable;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(detachable="true")
public class Sheet extends BaseEntity implements Serializable {

	private int month;

	private int year;

	public Sheet(Key key, int month, int year) {
		super();
		this.key = key;
		this.month = month;
		this.year = year;
	}

	/**
	 * @return the month
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * @param month
	 *            the month to set
	 */
	public void setMonth(int month) {
		this.month = month;
	}

	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @param year
	 *            the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Sheet [key=" + key + ", month=" + month + ", year=" + year
				+ "]";
	}
}
