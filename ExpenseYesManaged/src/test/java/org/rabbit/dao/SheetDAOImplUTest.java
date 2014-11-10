package org.rabbit.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.rabbit.common.BaseTest;
import org.rabbit.common.Constants;
import org.rabbit.dao.impl.SheetDAOImpl;
import org.rabbit.exception.SheetAlreadyExistsException;
import org.rabbit.exception.SheetNotFoundException;
import org.rabbit.model.Sheet;

public class SheetDAOImplUTest extends BaseTest {

	private SheetDAO	sheetDAO	= SheetDAOImpl.getInstance();

	@Test
	public void testCreateNewSheet() throws SheetAlreadyExistsException, SheetNotFoundException {
		// Persist Sheet entry.
		Sheet sheet = sheetDAO.createNewSheet(Constants.TEST_USER_ID, 12, 2013);

		// Retrieve it back from the database again.
		// sheet = sheetDAO.getSheet(12, 2013);

		// Assert each property against the expected values.
		Assert.assertNotNull(sheet);
		System.out.println(sheet.getCreatedOn());
		Assert.assertEquals(sheet.getMonth(), 12);
		Assert.assertEquals(sheet.getYear(), 2013);
		Assert.assertNotNull(sheet.getCreatedOn());
	}

	@Test
	public void testDeleteSheet() throws SheetAlreadyExistsException, SheetNotFoundException {

		// Persist Sheet entry.
		sheetDAO.createNewSheet(Constants.TEST_USER_ID, 12, 2013);
		sheetDAO.deleteSheet(Constants.TEST_USER_ID, 12, 2013);

		try {
			// Retrieve it back from the database again.
			sheetDAO.getSheet(Constants.TEST_USER_ID, 12, 2013);
			Assert.fail("It is expected to throw an error here");
		} catch (SheetNotFoundException onfe) {

		}
	}

	@Test
	public void testGetAllSheets() throws SheetAlreadyExistsException {
		sheetDAO.createNewSheet(Constants.TEST_USER_ID, 10, 2013);
		sheetDAO.createNewSheet(Constants.TEST_USER_ID, 11, 2013);
		sheetDAO.createNewSheet(Constants.TEST_USER_ID, 12, 2013);

		List<Sheet> sheetsList = sheetDAO.getAllSheets(Constants.TEST_USER_ID);
		Assert.assertNotNull(sheetsList);
		Assert.assertEquals(3, sheetsList.size());
	}

	@Test
	public void testGetSheet() throws SheetAlreadyExistsException, SheetNotFoundException {
		sheetDAO.createNewSheet(Constants.TEST_USER_ID, 12, 2013);
		// Retrieve it back from the database again.
		Sheet sheet = sheetDAO.getSheet(Constants.TEST_USER_ID, 12, 2013);
		Assert.assertNotNull(sheet);
	}

	@Test
	public void testUniqueIndexOfMonthAndYear() {
		try {
			sheetDAO.createNewSheet(Constants.TEST_USER_ID, 12, 2013);
			sheetDAO.createNewSheet(Constants.TEST_USER_ID, 12, 2013);
			Assert.fail("It is expected to throw an exception here...");
		} catch (SheetAlreadyExistsException e) {

		}
	}

}
