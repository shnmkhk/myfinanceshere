package org.rabbit.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.rabbit.common.BaseTest;
import org.rabbit.common.Constants;
import org.rabbit.dao.impl.EntryDAOImpl;
import org.rabbit.dao.impl.SheetDAOImpl;
import org.rabbit.dao.impl.TransactionDAOImpl;
import org.rabbit.exception.EntryAlreadyExistsException;
import org.rabbit.exception.SheetAlreadyExistsException;
import org.rabbit.exception.TransactionNotFoundException;
import org.rabbit.model.Entry;
import org.rabbit.model.Sheet;
import org.rabbit.model.Transaction;

public class TransactionDAOImplUTest extends BaseTest {

	private TransactionDAO transactionDAO = TransactionDAOImpl.getInstance();
	private EntryDAO entryDAO = EntryDAOImpl.getInstance();
	private SheetDAO sheetDAO = SheetDAOImpl.getInstance();

	@Test
	public void testCreateNewTransaction() throws SheetAlreadyExistsException,
			EntryAlreadyExistsException, TransactionNotFoundException {
		Sheet sheet = sheetDAO.createNewSheet(Constants.TEST_USER_ID, 12, 2013);
		Entry entry = entryDAO.createNewEntry('I', 33000,
				"APRIL_MONTH_SALARY", "April Month Salary", 'S', sheet);
		Transaction transaction = transactionDAO.createNewTransaction(
				"Description One", 20000, 1500, entry);
		int sequenceIndex = transaction.getSequenceIndex();

		Transaction transaction2 = transactionDAO
				.getTransactionByEntryAndIndex(entry, sequenceIndex);
		Assert.assertNotNull(transaction2);
		Assert.assertEquals("Description One", transaction2.getDescription());
		Assert.assertEquals(20000, transaction2.getOpeningBalance(), 0);
		Assert.assertEquals(1500, transaction2.getTransactionAmount(), 0);
	}

	@Test(expected = TransactionNotFoundException.class)
	public void testDeleteTransaction() throws SheetAlreadyExistsException,
			EntryAlreadyExistsException, TransactionNotFoundException {
		Sheet sheet = sheetDAO.createNewSheet(Constants.TEST_USER_ID, 12, 2013);
		Entry entry = entryDAO.createNewEntry('I', 33000,
				"APRIL_MONTH_SALARAY", "April Month Salary", 'S', sheet);
		Transaction transaction = transactionDAO.createNewTransaction(
				"Description One", 20000, 1500, entry);

		int sequenceIndex = transaction.getSequenceIndex();
		transactionDAO.deleteTransaction(entry, sequenceIndex);
		transactionDAO.getTransactionByEntryAndIndex(entry, sequenceIndex);
	}

	@Test
	public void testGetAllTransactions() throws SheetAlreadyExistsException,
			EntryAlreadyExistsException {
		Sheet sheet = sheetDAO.createNewSheet(Constants.TEST_USER_ID, 12, 2013);
		Entry entry = entryDAO.createNewEntry('I', 33000,
				"APRIL_MONTH_SALARAY", "April Month Salary", 'S', sheet);

		transactionDAO.createNewTransaction(
				"Description One", 20000, 1500, entry);
		transactionDAO.createNewTransaction(
				"Description Two", 18500, 1500, entry);
		transactionDAO.createNewTransaction(
				"Description Three", 17000, 1500, entry);

		List<Transaction> allTransactions = transactionDAO.getAllTransactions();
		Assert.assertNotNull(allTransactions);
		Assert.assertEquals(3, allTransactions.size());
	}

	@Test
	public void testGetTransactionByEntry() throws SheetAlreadyExistsException,
			EntryAlreadyExistsException {
		Sheet sheet = sheetDAO.createNewSheet(Constants.TEST_USER_ID, 12, 2013);
		Entry entry = entryDAO.createNewEntry('I', 33000,
				"APRIL_MONTH_SALARAY", "April Month Salary", 'S', sheet);

		transactionDAO.createNewTransaction(
				"Description One", 20000, 1500, entry);
		transactionDAO.createNewTransaction(
				"Description Two", 18500, 1500, entry);
		transactionDAO.createNewTransaction(
				"Description Three", 17000, 1500, entry);

		List<Transaction> allTransactions = transactionDAO
				.getTransactionByEntry(entry);
		Assert.assertNotNull(allTransactions);
		Assert.assertEquals(3, allTransactions.size());

	}

	@Test
	public void testGetTransactionByEntryAndIndex()
			throws SheetAlreadyExistsException, EntryAlreadyExistsException,
			TransactionNotFoundException {
		Sheet sheet = sheetDAO.createNewSheet(Constants.TEST_USER_ID, 12, 2013);
		Entry entry = entryDAO.createNewEntry('I', 33000,
				"APRIL_MONTH_SALARAY", "April Month Salary", 'S', sheet);

		transactionDAO.createNewTransaction(
				"Description One", 20000, 1500, entry);
		transactionDAO.createNewTransaction(
				"Description Two", 18500, 1500, entry);
		Transaction transactionThree = transactionDAO.createNewTransaction(
				"Description Three", 17000, 1500, entry);

		Transaction matchedTransaction = transactionDAO
				.getTransactionByEntryAndIndex(entry,
						transactionThree.getSequenceIndex());
		Assert.assertNotNull(matchedTransaction);
		Assert.assertEquals("Description Three",
				matchedTransaction.getDescription());
	}

	@Test
	public void testUpdateTransaction() throws SheetAlreadyExistsException,
			EntryAlreadyExistsException, TransactionNotFoundException {
		Sheet sheet = sheetDAO.createNewSheet(Constants.TEST_USER_ID, 12, 2013);
		Entry entry = entryDAO.createNewEntry('I', 33000,
				"APRIL_MONTH_SALARAY", "April Month Salary", 'S', sheet);

		Transaction transactionOne = transactionDAO.createNewTransaction(
				"Description One", 20000, 1500, entry);

		transactionOne.setDescription("Updated Description One");
		transactionDAO.updateTransaction(transactionOne);

		Transaction transactionEval = transactionDAO
				.getTransactionByEntryAndIndex(entry,
						transactionOne.getSequenceIndex());
		Assert.assertNotNull(transactionEval);
		Assert.assertEquals("Updated Description One",
				transactionEval.getDescription());

	}

}
