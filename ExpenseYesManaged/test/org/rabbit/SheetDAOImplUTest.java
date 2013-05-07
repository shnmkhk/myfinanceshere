package org.rabbit;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rabbit.dao.SheetDAO;
import org.rabbit.dao.impl.SheetDAOImpl;
import org.rabbit.exception.SheetAlreadyExistsException;
import org.rabbit.exception.SheetNotFoundException;
import org.rabbit.model.Sheet;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class SheetDAOImplUTest {

	private SheetDAO sheetDAO = SheetDAOImpl.getInstance();

	private final static LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());

	@Before
	public void setUp() {
		helper.setUp();
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}

	@Test
	public void testCreateNewSheet() throws SheetAlreadyExistsException,
			SheetNotFoundException {
		// Persist Sheet entry.
		sheetDAO.createNewSheet(12, 2013);

		// Retrieve it back from the database again.
		Sheet sheet = sheetDAO.getSheet(12, 2013);
		
		// Assert each property against the expected values.
		Assert.assertNotNull(sheet);
		Assert.assertEquals(sheet.getMonth(), 12);
		Assert.assertEquals(sheet.getYear(), 2013);
	}

	@Test
	public void testDeleteSheet() throws SheetAlreadyExistsException,
			SheetNotFoundException {

		// Persist Sheet entry.
		sheetDAO.createNewSheet(12, 2013);
		sheetDAO.deleteSheet(12, 2013);

		try {
			// Retrieve it back from the database again.
			sheetDAO.getSheet(12, 2013);
			Assert.fail("It is expected to throw an error here");
		} catch (SheetNotFoundException onfe) {

		}
	}

	@Test
	public void testGetAllSheets() throws SheetAlreadyExistsException {
		sheetDAO.createNewSheet(10, 2013);
		sheetDAO.createNewSheet(11, 2013);
		sheetDAO.createNewSheet(12, 2013);

		List<Sheet> sheetsList = sheetDAO.getAllSheets(Sheet.class
				.getSimpleName());
		Assert.assertNotNull(sheetsList);
		Assert.assertEquals(3, sheetsList.size());
	}

	@Test
	public void testGetSheet() throws SheetAlreadyExistsException,
			SheetNotFoundException {
		sheetDAO.createNewSheet(12, 2013);
		// Retrieve it back from the database again.
		Sheet sheet = sheetDAO.getSheet(12, 2013);
		Assert.assertNotNull(sheet);
	}

	@Test
	public void testUniqueIndexOfMonthAndYear() {
		try {
			sheetDAO.createNewSheet(12, 2013);
			sheetDAO.createNewSheet(12, 2013);
			Assert.fail("It is expected to throw an exception here...");
		} catch (SheetAlreadyExistsException e) {

		}
	}

}
