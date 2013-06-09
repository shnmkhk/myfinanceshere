package org.rabbit.services.impl;

import java.util.List;

import org.rabbit.dao.impl.SheetDAOImpl;
import org.rabbit.exception.SheetAlreadyExistsException;
import org.rabbit.exception.SheetNotFoundException;
import org.rabbit.model.Sheet;
import org.rabbit.services.SheetService;

/**
 * Service class implementation for loading/ persisting activities on <br/> 
 * Sheet entity 
 * 
 * @author shanmukha.k@gmail.com <br/>
 * for <b>Rabbit Computing, Inc.</b> <br/><br/> 
 * Date created: 01-Jun-2013
 */
public class SheetServiceImpl implements SheetService {

	private SheetServiceImpl(){}
	
	private static class SheetServiceImplHolder {
		public static final SheetServiceImpl SHEET_SERVICE_INSTANCE = new SheetServiceImpl();
	}
	
	public static final SheetServiceImpl getInstance(){
		return SheetServiceImplHolder.SHEET_SERVICE_INSTANCE;
	}

	private void validateMonthAndYear(int month, int year) {
		if (month < 1 || month > 12){
			throw new IllegalArgumentException(String.format("Month cannot be %d, it should be in the limits [1-12]", month));
		}

		if (year < 0){
			throw new IllegalArgumentException(String.format("Year cannot be %d, it should be in a positive number", year));
		}
	}
	@Override
	public Sheet addNewSheet(int month, int year)
			throws SheetAlreadyExistsException {
		validateMonthAndYear(month, year);
		return SheetDAOImpl.getInstance().createNewSheet(month, year);
	}

	@Override
	public List<Sheet> getAllSheets() {
		return SheetDAOImpl.getInstance().getAllSheets();
	}

	@Override
	public Sheet getSheet(int month, int year) throws SheetNotFoundException {
		validateMonthAndYear(month, year);
		return SheetDAOImpl.getInstance().getSheet(month, year);
	}

	@Override
	public void deleteSheet(int month, int year) throws SheetNotFoundException {
		validateMonthAndYear(month, year);
		SheetDAOImpl.getInstance().deleteSheet(month, year);
	}
}