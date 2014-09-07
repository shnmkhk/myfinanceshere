/**
 * 
 */
package org.rabbit.services;


import java.util.List;
import java.util.Map;

import org.rabbit.exception.EntryAlreadyExistsException;
import org.rabbit.exception.EntryNotFoundException;
import org.rabbit.exception.SheetNotFoundException;
import org.rabbit.model.Entry;
import org.rabbit.model.Sheet;
import org.rabbit.wrappers.EntryStatusWrapper;

import com.google.appengine.api.datastore.Key;

/**
 * Service layer interface for 'Entry'
 * 
 * @author shanmukha.k@gmail.com <br/>
 * for <b>Rabbit Computing, Inc.</b> <br/><br/> 
 * Date created: 02-Jun-2013
 */
public interface EntryService {
	
	/**
	 * Adds a new entry object into db for the specified 
	 * 
	 * @param type
	 * @param amount
	 * @param shortCode
	 * @param description
	 * @param status
	 * @return
	 * @throws EntryAlreadyExistsException
	 */
	public Entry addANewEntry(char type, double amount, String shortCode,
			String description, char status, Sheet parentSheet) throws EntryAlreadyExistsException;

	/**
	 * Adds a new entry object into db for the specified 
	 * 
	 * @param type
	 * @param amount
	 * @param shortCode
	 * @param description
	 * @param status
	 * @param categoryLabel
	 * @return
	 * @throws EntryAlreadyExistsException
	 */
	public Entry addANewEntry(char type, double amount, String shortCode,
			String description, char status, Sheet parentSheet, String categoryLabel) throws EntryAlreadyExistsException;
	
	/**
	 * Deletes entry object matched for the given associated sheet key and 
	 * sequence index.
	 * 
	 * @param parentSheetKey
	 * @param sequenceIndex
	 * @return
	 * @throws EntryNotFoundException
	 * @throws SheetNotFoundException 
	 */
	public boolean deleteEntry(Key parentSheetKey, int sequenceIndex) throws EntryNotFoundException, SheetNotFoundException;
	
	
	/**
	 * Returns all the entries across all the sheets in the db.
	 * 
	 * @return
	 */
	public List<Entry> getAllEntries();
	
	/**
	 * Returns the entry list associated with the given
	 * sheet key
	 * 
	 * @param parentSheetKey
	 * @return
	 * @throws SheetNotFoundException 
	 */
	public List<Entry> getEntries(Key parentSheetKey) throws SheetNotFoundException;
	
	/**
	 * Returns the unique entry associated with the given
	 * sheet key and the unique sequence id across given sheet.
	 * 
	 * @param parentSheetKey
	 * @param sequenceIndex
	 * @return
	 * @throws EntryNotFoundException
	 * @throws SheetNotFoundException 
	 */
	public Entry getEntryBySheetAndIndex(Key parentSheetKey, int sequenceIndex) throws EntryNotFoundException, SheetNotFoundException;
	
	/**
	 * Updates the specified entry instance with the 
	 * properties set to it
	 * 
	 * @param entry
	 * @return
	 */
	public Entry updateEntry(Entry entry);

	/**
	 * Adds multiple entries in one-go
	 * 
	 * @param name
	 * @param parameterMap
	 * @return
	 */
	public EntryStatusWrapper addMultipleEntries(String name, Map<String, String> parameterMap);
}
