package org.rabbit.services.dwr.impl;

import java.util.HashMap;
import java.util.List;

import org.rabbit.exception.EntryAlreadyExistsException;
import org.rabbit.exception.EntryNotFoundException;
import org.rabbit.exception.SheetNotFoundException;
import org.rabbit.model.Entry;
import org.rabbit.model.Sheet;
import org.rabbit.services.dwr.DWREntryService;
import org.rabbit.wrappers.EntryStatusWrapper;

import com.google.appengine.api.datastore.Key;

public class DWREntryServiceImpl implements DWREntryService {

	public Entry addANewEntry(char type, double amount, String shortCode,
			String description, char status, Sheet parentSheet)
			throws EntryAlreadyExistsException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean deleteEntry(Key parentSheetKey, int sequenceIndex)
			throws EntryNotFoundException, SheetNotFoundException {
		// TODO Auto-generated method stub
		return false;
	}

	public List<Entry> getAllEntries() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Entry> getEntries(Key parentSheetKey)
			throws SheetNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	public Entry getEntryBySheetAndIndex(Key parentSheetKey, int sequenceIndex)
			throws EntryNotFoundException, SheetNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	public Entry updateEntry(Entry entry) {
		// TODO Auto-generated method stub
		return null;
	}

	public EntryStatusWrapper addMultipleEntries(String name,
			HashMap localHashMap) {
		// TODO Auto-generated method stub
		return null;
	}

}
