package org.rabbit.dao;

import java.util.List;

import org.rabbit.common.EntryCategory;
import org.rabbit.exception.EntryAlreadyExistsException;
import org.rabbit.exception.EntryNotFoundException;
import org.rabbit.model.Entry;
import org.rabbit.model.Sheet;

/**
 * DAO Interface for Entry
 * 
 * @author shanmukha.k@gmail.com <br/>
 *         for <b>Rabbit Computing, Inc.</b> <br/>
 * <br/>
 *         Date created: 01-May-2013
 */
public interface EntryDAO {

	/**
	 * Creates a new entry with given type, amount, short code and description
	 * 
	 * @param type
	 *            'E' for expenditure and 'I' for income
	 * @param amount
	 *            in current locale currency
	 * @param shortCode
	 * @param description
	 * @param status
	 * @param sheet
	 * @return
	 */
	public Entry createNewEntry(char type, double amount, String shortCode, String description, char status, Sheet sheet, EntryCategory entryCategory) throws EntryAlreadyExistsException;

	/**
	 * Creates a new entry with given type, amount, short code and description
	 * 
	 * @param type
	 *            'E' for expenditure and 'I' for income
	 * @param amount
	 *            in current locale currency
	 * @param shortCode
	 * @param description
	 * @param status
	 * @param sheet
	 * @return
	 */
	public Entry createNewEntry(char type, double amount, String shortCode, String description, char status, Sheet sheet) throws EntryAlreadyExistsException;

	/**
	 * Deletes an entry specific to given sheet and sequence id.
	 * 
	 * @param sheet
	 * @param sequenceId
	 * @return
	 */
	public boolean deleteEntry(Sheet sheet, int sequenceIndex) throws EntryNotFoundException;

	/**
	 * Returns all the entries available in the database
	 * 
	 * @return
	 */
	public List<Entry> getAllEntries();

	/**
	 * Returns all the entries under specified sheet.
	 * 
	 * @param sheet
	 * @return
	 */
	public List<Entry> getEntriesBySheet(Sheet sheet);

	/**
	 * Updates existing entry with the new values set to the entry
	 * 
	 * @param entry
	 * @return
	 */
	public Entry updateEntry(Entry entry);

	/**
	 * Returns an entry associated with the given sheet and given sequence
	 * index.
	 * 
	 * @param sheet
	 * @param sequenceIndex
	 * @return
	 * @throws EntryNotFoundException
	 */
	public Entry getEntryBySheetAndIndex(Sheet sheet, int sequenceIndex) throws EntryNotFoundException;

	/**
	 * Sets the given entry category to the given entry.
	 * 
	 * @param entry
	 * @param entryCategory
	 */
	public void setEntryCategory(Entry entry, EntryCategory entryCategory);
}