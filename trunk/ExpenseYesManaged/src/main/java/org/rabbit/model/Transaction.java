package org.rabbit.model;

import java.io.Serializable;

import javax.jdo.annotations.ForeignKey;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(detachable="true", identityType=IdentityType.APPLICATION)
public class Transaction extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 4351901171868709445L;

	@Persistent
	private double openingBalance;

	@Persistent
	private String description;

	@Persistent
	private double transactionAmount;
	
	@Persistent
	private int sequenceIndex;
	
	@ForeignKey
	private Entry entry;
	
	
	public Transaction() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Transaction(Key key, double openingBalance, String description,
			double transactionAmount, int sequenceIndex) {
		super();
		this.key = key;
		this.openingBalance = openingBalance;
		this.description = description;
		this.transactionAmount = transactionAmount;
		this.sequenceIndex = sequenceIndex;
	}

	/**
	 * @return the openingBalance
	 */
	public double getOpeningBalance() {
		return openingBalance;
	}

	/**
	 * @param openingBalance
	 *            the openingBalance to set
	 */
	public void setOpeningBalance(double openingBalance) {
		this.openingBalance = openingBalance;
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
	 * @return the transactionAmount
	 */
	public double getTransactionAmount() {
		return transactionAmount;
	}

	/**
	 * @param transactionAmount
	 *            the transactionAmount to set
	 */
	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public Entry getEntry() {
		return entry;
	}

	public void setEntry(Entry entry) {
		this.entry = entry;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public int getSequenceIndex() {
		return sequenceIndex;
	}

	public void setSequenceIndex(int sequenceIndex) {
		this.sequenceIndex = sequenceIndex;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	
	public String toString() {
		return "Transaction [key=" + key + ", openingBalance=" + openingBalance
				+ ", description=" + description + ", transactionAmount="
				+ transactionAmount + ", sequenceIndex=" + sequenceIndex
				+ ", entry=" + entry + "]";
	}
}
