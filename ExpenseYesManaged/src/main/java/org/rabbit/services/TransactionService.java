/**
 * 
 */
package org.rabbit.services;

import java.util.List;

import org.rabbit.exception.TransactionNotFoundException;
import org.rabbit.model.Entry;
import org.rabbit.model.Transaction;

/**
 * Service layer for transaction model
 * 
 * @author shanmukha.k@gmail.com <br/>
 *         for <b>Rabbit Computing, Inc.</b> <br/>
 * <br/>
 *         Date created: 08-Jun-2013
 */
public interface TransactionService {

	public Transaction addNewTransaction(String description, double openingBalance, double transactionAmount, Entry entry);

	public void deleteTransaction(Entry entry, int sequenceIndex) throws TransactionNotFoundException;

	public List<Transaction> getAllTransactions();

	public List<Transaction> getTransactionByEntry(Entry entry);

	public Transaction getTransactionByEntryAndIndex(Entry entry, int sequenceIndex) throws TransactionNotFoundException;

	public Transaction updateTransaction(Transaction transaction);

}
