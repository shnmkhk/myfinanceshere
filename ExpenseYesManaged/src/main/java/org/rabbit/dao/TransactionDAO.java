package org.rabbit.dao;

import java.util.List;

import org.rabbit.exception.TransactionNotFoundException;
import org.rabbit.model.Entry;
import org.rabbit.model.Transaction;

/**
 * DAO Interface for Transaction entity
 * 
 * @author shanmukha.k@gmail.com <br/>
 *         for <b>Rabbit Computing, Inc.</b> <br/>
 * <br/>
 *         Date created: 01-May-2013
 */
public interface TransactionDAO {

	/**
	 * Creates a new transaction entry with given description and amount under
	 * the given entry.
	 * 
	 * @param description
	 * @param transactionAmount
	 * @param openingBalance
	 * @return
	 */
	public Transaction createNewTransaction(String description, double openingBalance, double transactionAmount, Entry entry);

	/**
	 * Deletes the transaction matching the entry and given sequence index.
	 * 
	 * @param entry
	 * @param sequenceIndex
	 * @throws TransactionNotFoundException
	 */
	public void deleteTransaction(Entry entry, int sequenceIndex) throws TransactionNotFoundException;

	/**
	 * Returns all the transaction available in the database across all the
	 * entries.
	 * 
	 * @return
	 */
	public List<Transaction> getAllTransactions();

	/**
	 * Returns the transactions under specified entry
	 * 
	 * @param entry
	 * @return
	 */
	public List<Transaction> getTransactionByEntry(Entry entry);

	/**
	 * Returns the transaction associated with the given sequence index under
	 * the given entry
	 * 
	 * @param entry
	 * @param sequenceIndex
	 * @return
	 */
	public Transaction getTransactionByEntryAndIndex(Entry entry, int sequenceIndex) throws TransactionNotFoundException;

	/**
	 * Updates the existing transaction with the newly set transaction object.
	 * 
	 * @param transaction
	 * @return
	 */
	public Transaction updateTransaction(Transaction transaction);

}
