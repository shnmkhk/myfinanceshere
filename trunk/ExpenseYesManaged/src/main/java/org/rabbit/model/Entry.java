package org.rabbit.model;

import java.io.Serializable;

import javax.jdo.annotations.ForeignKey;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(detachable="true", identityType=IdentityType.APPLICATION)
public class Entry extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1910736454574078429L;

	@Persistent
	private int sequenceIndex;

	@Persistent
	private char type;

	@Persistent
	private double amount;

	@Persistent
	private String shortCode;

	@Persistent
	private String description;

	@Persistent
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