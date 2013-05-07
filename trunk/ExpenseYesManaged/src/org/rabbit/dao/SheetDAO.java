package org.rabbit.dao;

import java.util.List;

import org.rabbit.exception.SheetAlreadyExistsException;
import org.rabbit.exception.SheetNotFoundException;
import org.rabbit.model.Sheet;

import com.google.appengine.api.datastore.Key;

/**
 * DAO interface for Sheet entity.
 * 
 * @author Shanmukha
 */
public interface SheetDAO {

	/**
	 * Creates a new sheet entry with given month and year.
	 * 
	 * @param month
	 * @param year
	 */
	public Sheet createNewSheet(int month, int year) throws SheetAlreadyExistsException;

	/**
	 * Removes an existing sheet entry matching the specified month and year.
	 * 
	 * @param month
	 * @param year
	 * @return
	 */
	public void deleteSheet(int month, int year) throws SheetNotFoundException;

	/**
	 * Returns all the sheet entries available in the database of given kind.
	 * Eg. "Sheet"
	 * 
	 * @param kind
	 * @return
	 */
	public List<Sheet> getAllSheets(String kind);

	/**
	 * Gets the unique sheet entry from the database matching the given month
	 * and year.
	 * 
	 * @param month
	 * @param year
	 * @return
	 */
	public Sheet getSheet(int month, int year) throws SheetNotFoundException;

	/**
	 * Gets the sheet entity based on its key
	 * 
	 * @param sheetKey
	 * @return
	 * @throws SheetNotFoundException
	 */
	public Sheet getSheet(Key sheetKey) throws SheetNotFoundException;

}
