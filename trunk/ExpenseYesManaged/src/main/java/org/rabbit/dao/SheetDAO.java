package org.rabbit.dao;

import java.util.List;
import java.util.Map;

import org.rabbit.exception.SheetAlreadyExistsException;
import org.rabbit.exception.SheetNotFoundException;
import org.rabbit.model.Sheet;

import com.google.appengine.api.datastore.Key;

/**
 * DAO Interface for Sheet entity.
 * 
 * @author shanmukha.k@gmail.com <br/>
 *         for <b>Rabbit Computing, Inc.</b> <br/>
 * <br/>
 *         Date created: 01-May-2013
 */
public interface SheetDAO {

	/**
	 * Creates a new sheet entry with given month and year.
	 * 
	 * @param month
	 * @param year
	 */
	public Sheet createNewSheet(String userId, int month, int year) throws SheetAlreadyExistsException;

	/**
	 * Removes an existing sheet entry matching the specified month and year.
	 * 
	 * @param month
	 * @param year
	 * @return
	 */
	public void deleteSheet(String userId, int month, int year) throws SheetNotFoundException;

	/**
	 * Returns all the sheet entries available in the database of Sheet kind.
	 * 
	 * @return
	 */
	public List<Sheet> getAllSheets(String userId);

	/**
	 * Returns all the available sheets keeping years in the keys and sheet
	 * entries in the values.
	 * 
	 * @param userId
	 * @return
	 */
	public Map<Integer, List<Sheet>> getAllSheetsMap(String userId);

	/**
	 * Gets the unique sheet entry from the database matching the given month
	 * and year.
	 * 
	 * @param month
	 * @param year
	 * @return
	 */
	public Sheet getSheet(String userId, int month, int year) throws SheetNotFoundException;

	/**
	 * Gets the sheet entity based on its key
	 * 
	 * @param sheetKey
	 * @return
	 * @throws SheetNotFoundException
	 */
	public Sheet getSheet(Key sheetKey) throws SheetNotFoundException;

}
