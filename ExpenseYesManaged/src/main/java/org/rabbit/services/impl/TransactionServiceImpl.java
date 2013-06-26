/**
 * 
 */
package org.rabbit.services.impl;

import java.util.List;

import org.rabbit.dao.TransactionDAO;
import org.rabbit.dao.impl.TransactionDAOImpl;
import org.rabbit.exception.TransactionNotFoundException;
import org.rabbit.model.Entry;
import org.rabbit.model.Transaction;
import org.rabbit.services.TransactionService;

/**
 * Service layer implementation for Transaction
 * 
 * @author shanmukha.k@gmail.com <br/>
 * for <b>Rabbit Computing, Inc.</b> <br/><br/> 
 * Date created: 08-Jun-2013
 */
public class TransactionServiceImpl implements TransactionService {

	private TransactionServiceImpl(){
		// Do nothing
	}
	
	private static class TransactionServiceImplHolder { 
		public static final TransactionServiceImpl TRANSACTION_SERVICE_IMPL = new TransactionServiceImpl();
	}
	
	public static TransactionServiceImpl getInstance(){
		return TransactionServiceImplHolder.TRANSACTION_SERVICE_IMPL;
	}
	
	private final TransactionDAO transactionDAO = TransactionDAOImpl.getInstance();
	
			
	/* (non-Javadoc)
	 * @see org.rabbit.services.TransactionService#addNewTransaction(java.lang.String, double, double, org.rabbit.model.Entry)
	 */
	
	public Transaction addNewTransaction(String description,
			double openingBalance, double transactionAmount, Entry entry) {
		return transactionDAO.createNewTransaction(description, openingBalance, transactionAmount, entry);
	}

	/* (non-Javadoc)
	 * @see org.rabbit.services.TransactionService#deleteTransaction(org.rabbit.model.Entry, int)
	 */
	
	public void deleteTransaction(Entry entry, int sequenceIndex) throws TransactionNotFoundException {
		transactionDAO.deleteTransaction(entry, sequenceIndex);
	}

	/* (non-Javadoc)
	 * @see org.rabbit.services.TransactionService#getAllTransactions()
	 */
	
	public List<Transaction> getAllTransactions() {
		return transactionDAO.getAllTransactions();
	}

	/* (non-Javadoc)
	 * @see org.rabbit.services.TransactionService#getTransactionByEntry(org.rabbit.model.Entry)
	 */
	
	public List<Transaction> getTransactionByEntry(Entry entry) {
		return transactionDAO.getTransactionByEntry(entry);
	}

	/* (non-Javadoc)
	 * @see org.rabbit.services.TransactionService#getTransactionByEntryAndIndex(org.rabbit.model.Entry, int)
	 */
	
	public Transaction getTransactionByEntryAndIndex(Entry entry,
			int sequenceIndex) throws TransactionNotFoundException {
		return transactionDAO.getTransactionByEntryAndIndex(entry, sequenceIndex);
	}

	/* (non-Javadoc)
	 * @see org.rabbit.services.TransactionService#updateTransaction(com.google.appengine.api.datastore.Transaction)
	 */
	
	public Transaction updateTransaction(Transaction transaction) {
		return transactionDAO.updateTransaction(transaction);
	}
}