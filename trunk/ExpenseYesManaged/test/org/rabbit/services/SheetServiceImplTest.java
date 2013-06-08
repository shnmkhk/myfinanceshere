package org.rabbit.services;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.rabbit.common.BaseTest;
import org.rabbit.exception.SheetAlreadyExistsException;
import org.rabbit.exception.SheetNotFoundException;
import org.rabbit.model.Sheet;
import org.rabbit.services.impl.SheetServiceImpl;

/**
 * Service level test case for Sheet entity.
 * 
 * @author shanmukha.k@gmail.com <br/>
 *         for <b>Rabbit Computing, Inc.</b> <br/>
 * <br/>
 *         Date created: 01-Jun-2013
 */
public class SheetServiceImplTest extends BaseTest {

	private final SheetService sheetService = SheetServiceImpl.getInstance();

	@Test(expected = IllegalArgumentException.class)
	public final void testAddNewSheetInvalidMonthNegative() throws SheetAlreadyExistsException {
		final int MONTH = -6;
		final int YEAR = 2013;
		sheetService.addNewSheet(MONTH, YEAR);
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testAddNewSheetInvalidMonthOutOfRange() throws SheetAlreadyExistsException {
		final int MONTH = 16;
		final int YEAR = 2013;
		sheetService.addNewSheet(MONTH, YEAR);
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testAddNewSheetInvalidYearNegative() throws SheetAlreadyExistsException {
		final int MONTH = 16;
		final int YEAR = -2013;
		sheetService.addNewSheet(MONTH, YEAR);
	}

	@Test
	public final void testAddNewSheetSuccess() throws SheetAlreadyExistsException {
		final int MONTH = 6;
		final int YEAR = 2013;
		Sheet sheet = sheetService.addNewSheet(MONTH, YEAR);
		Assert.assertNotNull(sheet);
		Assert.assertEquals(MONTH, sheet.getMonth());
		Assert.assertEquals(YEAR, sheet.getYear());
	}

	@Test
	public final void testGetAllSheetsSuccess() throws SheetAlreadyExistsException {
		int MONTH = 6;
		final int YEAR = 2013;
		sheetService.addNewSheet(MONTH, YEAR);
		sheetService.addNewSheet(++MONTH, YEAR);
		sheetService.addNewSheet(++MONTH, YEAR);
		sheetService.addNewSheet(++MONTH, YEAR);

		List<Sheet> availableSheets = sheetService.getAllSheets();
		Assert.assertNotNull(availableSheets);
		Assert.assertEquals(availableSheets.size(), 4);
	}

	@Test
	public final void testGetSheetSuccess() throws SheetNotFoundException, SheetAlreadyExistsException {
		int MONTH = 6;
		final int YEAR = 2013;
		sheetService.addNewSheet(MONTH, YEAR);
		Sheet sheet = sheetService.getSheet(MONTH, YEAR);
		Assert.assertNotNull(sheet);
		Assert.assertEquals(MONTH, sheet.getMonth());
		Assert.assertEquals(YEAR, sheet.getYear());
	}

	@Test(expected = SheetNotFoundException.class)
	public final void testGetSheetGetNonExistingSheet() throws SheetNotFoundException {
		int MONTH = 6;
		final int YEAR = 2013;
		sheetService.getSheet(MONTH, YEAR);
	}

	@Test(expected = SheetNotFoundException.class)
	public final void testDeleteSheetSuccess() throws SheetNotFoundException, SheetAlreadyExistsException {
		int MONTH = 6;
		final int YEAR = 2013;
		sheetService.addNewSheet(MONTH, YEAR);

		Sheet sheet = sheetService.getSheet(MONTH, YEAR);
		Assert.assertNotNull(sheet);
		Assert.assertEquals(MONTH, sheet.getMonth());
		Assert.assertEquals(YEAR, sheet.getYear());

		sheetService.deleteSheet(MONTH, YEAR);
		sheet = sheetService.getSheet(MONTH, YEAR);
	}
}