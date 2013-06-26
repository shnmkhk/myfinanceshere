package org.rabbit.model;

import java.io.Serializable;

import javax.jdo.annotations.PersistenceCapable;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(detachable="true")
public class Sheet extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -7567328471499565269L;

	private int month;

	private int year;

	public Sheet(Key key, int month, int year) {
		super();
		this.key = key;
		this.month = month;
		this.year = year;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
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

	
	public String toString() {
		return "Sheet [month=" + month + ", year=" + year + ", createdOn="
				+ createdOn + ", lastUpdatedOn=" + lastUpdatedOn
				+ ", createdBy=" + createdBy + ", lastUpdatedBy="
				+ lastUpdatedBy + "]";
	}
}
