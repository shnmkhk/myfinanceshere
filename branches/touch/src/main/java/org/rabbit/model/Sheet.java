package org.rabbit.model;

import java.io.Serializable;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import org.rabbit.common.Month;
import org.rabbit.services.dwr.vo.BaseAbstractVO;
import org.rabbit.services.dwr.vo.SheetVO;
import org.rabbit.shared.ObjectUtils;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(detachable = "true", identityType = IdentityType.APPLICATION)
public class Sheet extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -7567328471499565269L;

	@Persistent
	private int month;

	@Persistent
	private int year;

	@Persistent
	private String userId;

	public Sheet(Key key, int month, int year, String userId) {
		super();
		this.key = key;
		this.month = month;
		this.year = year;
		this.userId = userId;
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

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Sheet [month=" + month + ", year=" + year + ", userId="
				+ userId + "]";
	}

	public String getShortMonthStr() {
		return Month.shortMonthArr[month - 1];
	}

	public String getKeyStr() {
		return ObjectUtils.getSheetKeyStrForView(month, year);
	}

	private SheetVO sheetVO = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rabbit.model.BaseEntity#getVO()
	 */
	@Override
	public BaseAbstractVO getVO() {
		if (sheetVO == null) {
			sheetVO = new SheetVO();
		}

		sheetVO.setMonth(String.valueOf(month));
		sheetVO.setYear(String.valueOf(year));

		return sheetVO;
	}
}