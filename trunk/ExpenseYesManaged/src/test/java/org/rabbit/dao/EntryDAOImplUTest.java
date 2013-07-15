package org.rabbit.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.rabbit.common.BaseTest;
import org.rabbit.common.Constants;
import org.rabbit.dao.impl.EntryDAOImpl;
import org.rabbit.dao.impl.SheetDAOImpl;
import org.rabbit.exception.EntryAlreadyExistsException;
import org.rabbit.exception.EntryNotFoundException;
import org.rabbit.exception.SheetAlreadyExistsException;
import org.rabbit.model.Entry;
import org.rabbit.model.Sheet;

public class EntryDAOImplUTest extends BaseTest {

	private EntryDAO entryDAO = EntryDAOImpl.getInstance();
	private SheetDAO sheetDAO = SheetDAOImpl.getInstance();

	@Test
	public void testCreateNewEntry() throws EntryAlreadyExistsException,
			SheetAlreadyExistsException, EntryNotFoundException {
		Sheet sheet = sheetDAO.createNewSheet(Constants.TEST_USER_ID, 12, 2013);
		Entry entry = entryDAO.createNewEntry('I', 33000,
				"APRIL_MONTH_SALARAY", "April Month Salary", 'S', sheet);

		Entry entryEval = entryDAO.getEntryBySheetAndIndex(sheet,
				entry.getSequenceIndex());
		Assert.assertNotNull(entryEval);
		Assert.assertEquals(entry.getKey(), entryEval.getKey());
	}
	
	@Test
	public void testDeleteEntry() throws EntryAlreadyExistsException,
			SheetAlreadyExistsException {
		try {
			Sheet sheet = sheetDAO.createNewSheet(Constants.TEST_USER_ID, 12, 2013);
			Entry entry = entryDAO.createNewEntry('I', 33000,
					"APRIL_MONTH_SALARAY", "April Month Salary", 'S', sheet);

			entryDAO.getEntryBySheetAndIndex(sheet, entry.getSequenceIndex());
			entryDAO.deleteEntry(sheet, entry.getSequenceIndex());
			entryDAO.getEntryBySheetAndIndex(sheet, entry.getSequenceIndex());

			Assert.fail("It is expected to throw exception");
		} catch (EntryNotFoundException enfe) {
			// Ignore the exception, as it is expected to throw.
		}
	}
	
	@Test
	public void testGetAllEntries() throws EntryAlreadyExistsException, SheetAlreadyExistsException {
		Sheet sheet = sheetDAO.createNewSheet(Constants.TEST_USER_ID, 12, 2013);

		entryDAO.createNewEntry('I', 33000,
				"APRIL_MONTH_SALARAY", "April Month Salary", 'S', sheet);
		entryDAO.createNewEntry('E', 6000,
				"HOUSE_RENT", "House Rent", 'S', sheet);

		List<Entry> entries = entryDAO.getAllEntries();
		Assert.assertNotNull(entries);
		Assert.assertEquals(2, entries.size());
	}
	
	@Test
	public void testGetEntriesBySheet() throws SheetAlreadyExistsException, EntryAlreadyExistsException {
		Sheet dec_13 = sheetDAO.createNewSheet(Constants.TEST_USER_ID, 12, 2013);
		Sheet nov_13 = sheetDAO.createNewSheet(Constants.TEST_USER_ID, 11, 2013);

		entryDAO.createNewEntry('I', 33000,
				"DECEMBER_MONTH_SALARAY", "December Month Salary", 'S', dec_13);
		entryDAO.createNewEntry('E', 6000,
				"DECEMBER_HOUSE_RENT", "December House Rent", 'S', dec_13);
		entryDAO.createNewEntry('I', 33000,
				"NOVEMBER_MONTH_SALARAY", "November Month Salary", 'S', nov_13);
		entryDAO.createNewEntry('E', 6000,
				"NOVEMBER_HOUSE_RENT", "November House Rent", 'S', nov_13);

		List<Entry> entries = entryDAO.getEntriesBySheet(nov_13);
		Assert.assertNotNull(entries);
		Assert.assertEquals(2, entries.size());
	}
	
	@Test
	public void testUpdateEntry() throws SheetAlreadyExistsException, EntryAlreadyExistsException, EntryNotFoundException {
		Sheet dec_13 = sheetDAO.createNewSheet(Constants.TEST_USER_ID, 12, 2013);
		Entry decSalaryEntry = entryDAO.createNewEntry('I', 33000.00,
				"DECEMBER_MONTH_SALARAY", "December Month Salary", 'S', dec_13);
		entryDAO.createNewEntry('E', 6000,
				"DECEMBER_HOUSE_RENT", "December House Rent", 'S', dec_13);

		decSalaryEntry.setAmount(34000.00);
		entryDAO.updateEntry(decSalaryEntry);
		
		Entry decSalaryEntryEval = entryDAO.getEntryBySheetAndIndex(dec_13, decSalaryEntry.getSequenceIndex());
		Assert.assertNotNull(decSalaryEntryEval);
		Assert.assertEquals(34000.00, decSalaryEntryEval.getAmount(), 0);
	}
	
	@Test
	public void testGetEntryBySheetAndIndex() throws SheetAlreadyExistsException, EntryAlreadyExistsException, EntryNotFoundException {
		Sheet dec_13 = sheetDAO.createNewSheet(Constants.TEST_USER_ID, 12, 2013);
		Entry decSalaryEntry = entryDAO.createNewEntry('I', 33000.00,
				"DECEMBER_MONTH_SALARAY", "December Month Salary", 'S', dec_13);
		Entry decSalaryEntryEval = entryDAO.getEntryBySheetAndIndex(dec_13, decSalaryEntry.getSequenceIndex());
		Assert.assertNotNull(decSalaryEntryEval);
	}
	
	
	@Test
	public void testAddEntryCheckUnique() throws SheetAlreadyExistsException, EntryAlreadyExistsException, EntryNotFoundException {
		Sheet dec_13 = sheetDAO.createNewSheet(Constants.TEST_USER_ID, 12, 2013);
		Entry decSalaryEntry = entryDAO.createNewEntry('I', 33000.00,
				"DECEMBER_MONTH_SALARAY", "December Month Salary", 'S', dec_13);
		Entry decHouseRentEntry = entryDAO.createNewEntry('E', 6000.00,
				"DECEMBER_HOUSE_RENT", "December House Rent", 'S', dec_13);
		
		Entry decSalaryEntryEval = entryDAO.getEntryBySheetAndIndex(dec_13, decSalaryEntry.getSequenceIndex());
		Entry decHouseRentEntryEval = entryDAO.getEntryBySheetAndIndex(dec_13, decHouseRentEntry.getSequenceIndex());
		
		Assert.assertNotNull(decSalaryEntryEval);
		Assert.assertNotNull(decHouseRentEntryEval);
	}

	
}
