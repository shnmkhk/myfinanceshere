package org.rabbit.services.impl;

import java.util.List;

import org.rabbit.dao.EntryDAO;
import org.rabbit.dao.SheetDAO;
import org.rabbit.dao.impl.EntryDAOImpl;
import org.rabbit.dao.impl.SheetDAOImpl;
import org.rabbit.exception.EntryAlreadyExistsException;
import org.rabbit.exception.EntryNotFoundException;
import org.rabbit.exception.SheetNotFoundException;
import org.rabbit.model.Entry;
import org.rabbit.model.Sheet;
import org.rabbit.services.EntryService;

import com.google.appengine.api.datastore.Key;

/**
 * Service layer implementation for Entry model
 * 
 * @author shanmukha.k@gmail.com <br/>
 * for <b>Rabbit Computing, Inc.</b> <br/><br/> 
 * Date created: 02-Jun-2013
 */
public class EntryServiceImpl implements EntryService {

	private EntryServiceImpl(){
		// Do nothing
	}
	
	private static class EntryServiceImplHolder {
		public static EntryServiceImpl ENTRY_SERVICE_IMPL_SINGLETON_INSTANCE = new EntryServiceImpl();
	}
	
	public static EntryServiceImpl getInstance(){
		return EntryServiceImplHolder.ENTRY_SERVICE_IMPL_SINGLETON_INSTANCE;
	}
	
	private final EntryDAO entryDAO = EntryDAOImpl.getInstance();
	private final SheetDAO sheetDAO = SheetDAOImpl.getInstance();

	/* (non-Javadoc)
	 * @see org.rabbit.services.EntryService#addANewEntry(char, double, java.lang.String, java.lang.String, char, com.google.appengine.api.datastore.Key)
	 */
	
	public Entry addANewEntry(char type, double amount, String shortCode,
			String description, char status, Sheet sheet)
			throws EntryAlreadyExistsException {
		try {
			return entryDAO.createNewEntry(type, amount, shortCode, description, status, sheetDAO.getSheet(sheet.getMonth(), sheet.getYear()));
		} catch (SheetNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see org.rabbit.services.EntryService#deleteEntry(com.google.appengine.api.datastore.Key, int)
	 */
	
	public boolean deleteEntry(Key parentSheetKey, int sequenceIndex)
			throws EntryNotFoundException, SheetNotFoundException {
		Sheet parentSsheetForSpecifiedKey = sheetDAO.getSheet(parentSheetKey);
		if (parentSsheetForSpecifiedKey == null) {
			throw new SheetNotFoundException("Sheet doesn't exist for key: " + parentSheetKey);
		}
		return entryDAO.deleteEntry(parentSsheetForSpecifiedKey, sequenceIndex);
	}

	/* (non-Javadoc)
	 * @see org.rabbit.services.EntryService#getAllEntries()
	 */
	
	public List<Entry> getAllEntries() {
		return entryDAO.getAllEntries();
	}

	/* (non-Javadoc)
	 * @see org.rabbit.services.EntryService#getEntries(com.google.appengine.api.datastore.Key)
	 */
	
	public List<Entry> getEntries(Key parentSheetKey) throws SheetNotFoundException {
		return entryDAO.getEntriesBySheet(sheetDAO.getSheet(parentSheetKey));
	}

	/* (non-Javadoc)
	 * @see org.rabbit.services.EntryService#getEntryBySheetAndIndex(com.google.appengine.api.datastore.Key, int)
	 */
	
	public Entry getEntryBySheetAndIndex(Key parentSheetKey, int sequenceIndex)
			throws EntryNotFoundException, SheetNotFoundException {
		return entryDAO.getEntryBySheetAndIndex(sheetDAO.getSheet(parentSheetKey), sequenceIndex);
	}

	/* (non-Javadoc)
	 * @see org.rabbit.services.EntryService#updateEntry(org.rabbit.model.Entry)
	 */
	
	public Entry updateEntry(Entry entry) {
		return entryDAO.updateEntry(entry);
	}

}
