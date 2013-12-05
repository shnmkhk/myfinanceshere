/**
 * 
 */
package org.rabbit.services;

import static org.junit.Assert.fail;

import org.junit.Ignore;
import org.junit.Test;
import org.rabbit.common.BaseTest;
import org.rabbit.common.Constants;
import org.rabbit.exception.EntryAlreadyExistsException;
import org.rabbit.exception.SheetAlreadyExistsException;
import org.rabbit.model.Entry;
import org.rabbit.model.Sheet;
import org.rabbit.model.Transaction;
import org.rabbit.services.impl.EntryServiceImpl;
import org.rabbit.services.impl.SheetServiceImpl;
import org.rabbit.services.impl.TransactionServiceImpl;

/**
 * Service layer test case for Transaction model.
 * 
 * @author shanmukha.k@gmail.com <br/>
 * for <b>Rabbit Computing, Inc.</b> <br/><br/> 
 * Date created: 08-Jun-2013
 */
public class TransactionServiceImplTest extends BaseTest {

	private TransactionService transactionService = TransactionServiceImpl.getInstance();
	private EntryService entryService = EntryServiceImpl.getInstance();
	private SheetService sheetService = SheetServiceImpl.getInstance();
	
	
	/**
	 * Test method for {@link org.rabbit.services.impl.TransactionServiceImpl#addNewTransaction(java.lang.String, double, double, org.rabbit.model.Entry)}.
	 * @throws SheetAlreadyExistsException 
	 * @throws EntryAlreadyExistsException 
	 */
	@Test
	@Ignore
	public final void testAddNewTransaction() throws SheetAlreadyExistsException, EntryAlreadyExistsException {
		
		Sheet sheet = sheetService.addNewSheet(Constants.TEST_USER_ID, 12, 2013);
		Entry entry  = entryService.addANewEntry('I', 33000,
				"APRIL_MONTH_SALARY", "April Month Salary", 'S', sheet);
		Transaction transaction = transactionService.addNewTransaction(
				"Description One", 20000, 1500, entry);
		
	}

	/**
	 * Test method for {@link org.rabbit.services.impl.TransactionServiceImpl#deleteTransaction(org.rabbit.model.Entry, int)}.
	 */
	@Test
	@Ignore
	public final void testDeleteTransaction() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link org.rabbit.services.impl.TransactionServiceImpl#getAllTransactions()}.
	 */
	@Test
	@Ignore
	public final void testGetAllTransactions() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link org.rabbit.services.impl.TransactionServiceImpl#getTransactionByEntry(org.rabbit.model.Entry)}.
	 */
	@Test
	@Ignore
	public final void testGetTransactionByEntry() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link org.rabbit.services.impl.TransactionServiceImpl#getTransactionByEntryAndIndex(org.rabbit.model.Entry, int)}.
	 */
	@Test
	@Ignore
	public final void testGetTransactionByEntryAndIndex() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link org.rabbit.services.impl.TransactionServiceImpl#updateTransaction(org.rabbit.model.Transaction)}.
	 */
	@Test
	@Ignore
	public final void testUpdateTransaction() {
		fail("Not yet implemented"); // TODO
	}

}
