/**
 * 
 */
package org.rabbit.services;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rabbit.common.BaseTest;
import org.rabbit.exception.EntryAlreadyExistsException;
import org.rabbit.exception.EntryNotFoundException;
import org.rabbit.exception.SheetAlreadyExistsException;
import org.rabbit.exception.SheetNotFoundException;
import org.rabbit.model.Entry;
import org.rabbit.model.Sheet;
import org.rabbit.services.impl.EntryServiceImpl;
import org.rabbit.services.impl.SheetServiceImpl;
import org.rabbit.shared.ObjectUtils;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * Service level test case for Entry model.
 * 
 * @author shanmukha.k@gmail.com <br/>
 * for <b>Rabbit Computing, Inc.</b> <br/><br/> 
 * Date created: 05-Jun-2013
 */
public class EntryServiceImplTest extends BaseTest {

	private final EntryService entryService = EntryServiceImpl.getInstance();
	private final SheetService sheetService = SheetServiceImpl.getInstance();
	
	final int MONTH = 12;
	final int YEAR = 2013;
	
	final char ENTRY_TYPE_INCOME = 'I';
	final double AMOUNT = 33000;
	final String SHORT_CODE = "APRIL_MONTH_SALARAY";
	final String DESC = "April Month Salary";
	final char STATUS = 'S';
	Sheet sheet = null;
	Entry entry = null;
	
	@Before
	public final void preExecutionToEachTest() throws SheetAlreadyExistsException, EntryAlreadyExistsException{
		sheet = sheetService.addNewSheet(MONTH, YEAR);
		entry = entryService.addANewEntry(ENTRY_TYPE_INCOME, AMOUNT, SHORT_CODE, DESC, STATUS, sheet);
	}
	
	
	/**
	 * Test method for {@link org.rabbit.services.impl.EntryServiceImpl#addANewEntry(char, double, java.lang.String, java.lang.String, char, org.rabbit.model.Sheet)}.
	 * @throws SheetAlreadyExistsException 
	 * @throws EntryAlreadyExistsException 
	 */
	@Test
	public final void testAddANewEntrySuccess() throws SheetAlreadyExistsException, EntryAlreadyExistsException {
		Assert.assertNotNull(entry);
		
		entry = entryService.addANewEntry(ENTRY_TYPE_INCOME, AMOUNT, SHORT_CODE, DESC, STATUS, sheet);
		
		Assert.assertEquals(ENTRY_TYPE_INCOME, entry.getType());
		Assert.assertEquals(AMOUNT, entry.getAmount(), 0);
		Assert.assertEquals(SHORT_CODE, entry.getShortCode());
		Assert.assertEquals(DESC, entry.getDescription());
		Assert.assertEquals(STATUS, entry.getStatus());
	}

	/**
	 * Test method for {@link org.rabbit.services.impl.EntryServiceImpl#deleteEntry(com.google.appengine.api.datastore.Key, int)}.
	 * @throws SheetNotFoundException 
	 * @throws EntryNotFoundException 
	 */
	@Test(expected=EntryNotFoundException.class)
	public final void testDeleteEntry() throws EntryNotFoundException, SheetNotFoundException {
		Key dec13SheetKey = KeyFactory.createKey(Sheet.class.getSimpleName(), MONTH + "_" + YEAR);
		List<Entry> entryList = entryService.getEntries(dec13SheetKey);
		int sequenceIndex = entryList.get(0).getSequenceIndex();
		
		entryService.deleteEntry(dec13SheetKey, sequenceIndex);
		
		entryService.getEntryBySheetAndIndex(dec13SheetKey, sequenceIndex);
		Assert.fail("It is expected to throw EntryNotFoundException");
	}

	/**
	 * Test method for {@link org.rabbit.services.impl.EntryServiceImpl#getAllEntries()}.
	 * @throws EntryAlreadyExistsException 
	 */
	@Test
	public final void testGetAllEntries() throws EntryAlreadyExistsException {
		entry = entryService.addANewEntry(ENTRY_TYPE_INCOME, AMOUNT, SHORT_CODE, DESC, STATUS, sheet);
		entry = entryService.addANewEntry(ENTRY_TYPE_INCOME, AMOUNT, SHORT_CODE, DESC, STATUS, sheet);
		entry = entryService.addANewEntry(ENTRY_TYPE_INCOME, AMOUNT, SHORT_CODE, DESC, STATUS, sheet);
		entry = entryService.addANewEntry(ENTRY_TYPE_INCOME, AMOUNT, SHORT_CODE, DESC, STATUS, sheet);
		entry = entryService.addANewEntry(ENTRY_TYPE_INCOME, AMOUNT, SHORT_CODE, DESC, STATUS, sheet);

		List<Entry> allEntries = entryService.getAllEntries();
		Assert.assertEquals(6, allEntries.size());
	}

	/**
	 * Test method for {@link org.rabbit.services.impl.EntryServiceImpl#getEntries(com.google.appengine.api.datastore.Key)}.
	 * @throws EntryAlreadyExistsException 
	 * @throws SheetNotFoundException 
	 * @throws SheetAlreadyExistsException 
	 * @throws EntryNotFoundException 
	 */
	@Test
	public final void testGetEntries() throws EntryAlreadyExistsException, SheetNotFoundException, SheetAlreadyExistsException, EntryNotFoundException {
		// Remove entry and sheets that are created in pre-execution method.
		entryService.deleteEntry(sheet.getKey(), entry.getSequenceIndex());
		sheetService.deleteSheet(MONTH, YEAR);
		
		Sheet nov13Sheet = sheetService.addNewSheet(11, 2013);
		Sheet dec13Sheet = sheetService.addNewSheet(12, 2013);

		entry = entryService.addANewEntry(ENTRY_TYPE_INCOME, AMOUNT, SHORT_CODE, DESC, STATUS, nov13Sheet);
		entry = entryService.addANewEntry(ENTRY_TYPE_INCOME, AMOUNT, SHORT_CODE, DESC, STATUS, nov13Sheet);
		entry = entryService.addANewEntry(ENTRY_TYPE_INCOME, AMOUNT, SHORT_CODE, DESC, STATUS, nov13Sheet);

		entry = entryService.addANewEntry(ENTRY_TYPE_INCOME, AMOUNT, SHORT_CODE, DESC, STATUS, dec13Sheet);
		entry = entryService.addANewEntry(ENTRY_TYPE_INCOME, AMOUNT, SHORT_CODE, DESC, STATUS, dec13Sheet);

		List<Entry> nov13Entries = 
				entryService.getEntries(KeyFactory.createKey(Sheet.class.getSimpleName(), 11 + "_" + 2013));
		List<Entry> dec13Entries =
				entryService.getEntries(KeyFactory.createKey(Sheet.class.getSimpleName(), 12 + "_" + 2013));
		
		Assert.assertEquals(3, nov13Entries.size());
		Assert.assertEquals(2, dec13Entries.size());
	}

	/**
	 * Test method for {@link org.rabbit.services.impl.EntryServiceImpl#getEntryBySheetAndIndex(com.google.appengine.api.datastore.Key, int)}.
	 * @throws SheetNotFoundException 
	 * @throws EntryNotFoundException 
	 * @throws EntryAlreadyExistsException 
	 */
	@Test
	public final void testGetEntryBySheetAndIndex() throws EntryNotFoundException, SheetNotFoundException, EntryAlreadyExistsException {
		Key parentSheetKey = KeyFactory.createKey(Sheet.class.getSimpleName(), ObjectUtils.getSheetKeyId(MONTH, YEAR));
		entry = entryService.getEntryBySheetAndIndex(parentSheetKey, entry.getSequenceIndex());
		Assert.assertNotNull(entry);
		
		Assert.assertEquals(ENTRY_TYPE_INCOME, entry.getType());
		Assert.assertEquals(AMOUNT, entry.getAmount(), 0);
		Assert.assertEquals(SHORT_CODE, entry.getShortCode());
		Assert.assertEquals(DESC, entry.getDescription());
		Assert.assertEquals(STATUS, entry.getStatus());
	}

	/**
	 * Test method for {@link org.rabbit.services.impl.EntryServiceImpl#updateEntry(org.rabbit.model.Entry)}.
	 * @throws SheetNotFoundException 
	 * @throws EntryNotFoundException 
	 */
	@Test
	public final void testUpdateEntry() throws EntryNotFoundException, SheetNotFoundException {
		Key parentSheetKey = KeyFactory.createKey(Sheet.class.getSimpleName(), ObjectUtils.getSheetKeyId(MONTH, YEAR));
		Assert.assertNotNull(entry);
		
		entry.setAmount(35000);
		entryService.updateEntry(entry);
		
		entryService.getEntryBySheetAndIndex(parentSheetKey, entry.getSequenceIndex());
		
		Assert.assertEquals(ENTRY_TYPE_INCOME, entry.getType());
		Assert.assertEquals(35000, entry.getAmount(), 0);
		Assert.assertEquals(SHORT_CODE, entry.getShortCode());
		Assert.assertEquals(DESC, entry.getDescription());
		Assert.assertEquals(STATUS, entry.getStatus());
	}
}