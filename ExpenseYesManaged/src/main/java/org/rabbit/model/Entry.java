package org.rabbit.model;

import java.io.Serializable;

import javax.jdo.annotations.ForeignKey;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Entry implements Serializable {

	private static final long serialVersionUID = 1910736454574078429L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	private int sequenceIndex;

	private char type;

	private double amount;

	private String shortCode;

	private String description;

	private char status;
	
	@ForeignKey
	private Sheet sheet;
	
	public Entry(int sequenceIndex, char type, double amount, String shortCode,
			String description, char status){
		this.sequenceIndex = sequenceIndex;
		this.type = type;
		this.amount = amount;
		this.shortCode = shortCode;
		this.description = description;
		this.status = status;
	}
	public Entry(Key key, int sequenceIndex, char type, double amount, String shortCode,
			String description, char status) {
		super();
		this.key = key;
		this.sequenceIndex = sequenceIndex;
		this.type = type;
		this.amount = amount;
		this.shortCode = shortCode;
		this.description = description;
		this.status = status;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	/**
	 * The type of the transaction. Possible values are
	 * 'E' [Expense] or 'I' [Income]
	 * 
	 * @return the type
	 */
	public char getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(char type) {
		this.type = type;
	}

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * @return the shortCode
	 */
	public String getShortCode() {
		return shortCode;
	}

	/**
	 * @param shortCode
	 *            the shortCode to set
	 */
	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the status
	 */
	public char getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(char status) {
		this.status = status;
	}

	public int getSequenceIndex() {
		return sequenceIndex;
	}

	public void setSequenceIndex(int sequenceIndex) {
		this.sequenceIndex = sequenceIndex;
	}

	public Sheet getSheet() {
		return sheet;
	}

	public void setSheet(Sheet sheet) {
		this.sheet = sheet;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Entry [key=" + key + ", sequenceIndex=" + sequenceIndex
				+ ", type=" + type + ", amount=" + amount + ", shortCode="
				+ shortCode + ", description=" + description + ", status="
				+ status + ", sheet=" + sheet + "]";
	}
}