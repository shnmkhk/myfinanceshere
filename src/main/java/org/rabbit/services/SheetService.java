package org.rabbit.services;

import java.util.List;
import java.util.Map;

import org.rabbit.exception.SheetAlreadyExistsException;
import org.rabbit.exception.SheetNotFoundException;
import org.rabbit.model.Sheet;

/**
 * Service layer interface for 'Sheet'
 * 
 * @author shanmukha.k@gmail.com <br/>
 * for <b>Rabbit Computing, Inc.</b> <br/><br/> 
 * Date created: 02-Jun-2013
 */
public interface SheetService {

	/**
	 * Creates a new sheet into the system.
	 * 
	 * @param month
	 * @param year
	 * @return
	 * @throws SheetAlreadyExistsException
	 */
	public Sheet addNewSheet(String userId, int month, int year)
			throws SheetAlreadyExistsException;

	/**
	 * Returns all the available sheets in the system.
	 * 
	 * @return
	 */
	public List<Sheet> getAllSheets(String userId);
	
	/**
	 * Returns all the available sheets keeping years in the keys and sheet entries 
	 * in the values.
	 * 
	 * @param userId
	 * @return
	 */
	public Map<Integer, List<Sheet>> getAllSheetsMap(String userId);

	/**
	 * Returns the sheet of respective month, year 
	 * 
	 * @param month
	 * @param year
	 * @return
	 * @throws SheetNotFoundException
	 */
	public Sheet getSheet(String userId, int month, int year) throws SheetNotFoundException;

	/**
	 * Returns the sheet of respective month, year in mm_yyyy format
	 * 
	 * @param sheetKeyStr
	 * @return
	 * @throws SheetNotFoundException
	 */
	public Sheet getSheet(String userId, String sheetKeyStr) throws SheetNotFoundException;

	/**
	 * Deletes the sheet entry of the specified month and year.
	 * 
	 * @param month
	 * @param year
	 * @throws SheetNotFoundException
	 */
	public void deleteSheet(String userId, int month, int year) throws SheetNotFoundException;
	
	
}
