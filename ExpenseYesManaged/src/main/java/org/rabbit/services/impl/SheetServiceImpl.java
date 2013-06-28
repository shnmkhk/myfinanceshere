package org.rabbit.services.impl;

import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.rabbit.dao.impl.SheetDAOImpl;
import org.rabbit.exception.SheetAlreadyExistsException;
import org.rabbit.exception.SheetNotFoundException;
import org.rabbit.model.Sheet;
import org.rabbit.services.SheetService;
import org.rabbit.shared.NumUtil;

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

	private void validateMonthAndYear(int month, int year) throws IllegalArgumentException {
		if (month == NumUtil.MINUS_ONE || year == NumUtil.MINUS_ONE){
			throw new IllegalArgumentException(String.format("Invalid month or year"));
		} else {
			StringBuffer errorMsgBuf = new StringBuffer();
			if (month < NumUtil.MONTH_LOWER_LIMIT || month > NumUtil.MONTH_UPPER_LIMIT){
				errorMsgBuf.append(String.format("Month cannot be %d, it should be in the range [%d (January) to %d (December)]", month, NumUtil.MONTH_LOWER_LIMIT, NumUtil.MONTH_UPPER_LIMIT));
			}
	
			if (year < NumUtil.YEAR_LOWER_LIMIT || year > NumUtil.YEAR_UPPER_LIMIT){
				if (errorMsgBuf.length() > 0) {
					errorMsgBuf.append("<br/>");
				}
				errorMsgBuf.append(String.format("Year cannot be %d, it should be equal or later to 1970", year));
			}
			
			if (errorMsgBuf.length() > 0){
				throw new IllegalArgumentException(errorMsgBuf.toString());
			}
		}
	}
	
	
	public Sheet addNewSheet(int month, int year)
			throws SheetAlreadyExistsException, IllegalArgumentException {
		validateMonthAndYear(month, year);
		return SheetDAOImpl.getInstance().createNewSheet(month, year);
	}

	
	public List<Sheet> getAllSheets() {
		return SheetDAOImpl.getInstance().getAllSheets();
	}

	
	public Sheet getSheet(int month, int year) throws SheetNotFoundException, IllegalArgumentException {
		validateMonthAndYear(month, year);
		return SheetDAOImpl.getInstance().getSheet(month, year);
	}

	
	public void deleteSheet(int month, int year) throws SheetNotFoundException, IllegalArgumentException {
		validateMonthAndYear(month, year);
		SheetDAOImpl.getInstance().deleteSheet(month, year);
	}

	/* (non-Javadoc)
	 * @see org.rabbit.services.SheetService#getSheet(java.lang.String)
	 */
	public Sheet getSheet(String sheetKeyStr) throws SheetNotFoundException {
		int[] monthYr = org.rabbit.shared.ObjectUtils.getMonthYr(sheetKeyStr);
		return getSheet(monthYr[0], monthYr[1]);
	}
}