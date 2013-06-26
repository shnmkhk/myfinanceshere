package org.rabbit.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.jdo.PersistenceManager;

import org.rabbit.dao.EntryDAO;
import org.rabbit.dao.SheetDAO;
import org.rabbit.exception.EntryAlreadyExistsException;
import org.rabbit.exception.EntryNotFoundException;
import org.rabbit.exception.SheetNotFoundException;
import org.rabbit.model.Entry;
import org.rabbit.model.Sheet;
import org.rabbit.server.PMF;
import org.rabbit.shared.NumUtil;
import org.rabbit.shared.ObjectUtils;
import org.rabbit.shared.Util;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;

/**
 * DAO layer loading/ persisting implementation for Entry entity
 * 
 * @author shanmukha.k@gmail.com <br/>
 * for <b>Rabbit Computing, Inc.</b> <br/><br/> 
 * Date created: 01-May-2013
 */
public class EntryDAOImpl implements EntryDAO {

	private SheetDAO sheetDAO = SheetDAOImpl.getInstance();
	private EntryDAOImpl() {
		// Do nothing
	}

	private static class EntryDAOImplHandler {
		public static EntryDAOImpl entryDAOImpl = new EntryDAOImpl();
	}

	public static EntryDAOImpl getInstance() {
		return EntryDAOImplHandler.entryDAOImpl;
	}

	
	public Entry createNewEntry(char type, double amount, String shortCode,
			String description, char status, Sheet sheet)
			throws EntryAlreadyExistsException {

		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = new Query(Entry.class.getSimpleName(), sheet.getKey());
		query.addSort("sequenceIndex", SortDirection.DESCENDING);

		FetchOptions fetchOptions = FetchOptions.Builder.withLimit(1);
		List<Entity> entriesOfGivenSheet = (List<Entity>) Util.getDatastoreServiceInstance().prepare(query).asList(fetchOptions);
		Entity recentEntry = (entriesOfGivenSheet != null) && (entriesOfGivenSheet.size() > 0) ? entriesOfGivenSheet.get(0) : null;
		int maxSeqIx = 0;
		if (recentEntry != null) {
			maxSeqIx = NumUtil.getIntValue(
					recentEntry.getProperty("sequenceIndex").toString(), 0);
		}
		Key key = KeyFactory.createKey(sheet.getKey(),
				Entry.class.getSimpleName(),
				ObjectUtils.getEntryKeyId(sheet.getKey(), ++maxSeqIx));

		Entry entry = new Entry(key, maxSeqIx, type, amount, shortCode,
				description, status);

		entry.setCreatedOn(Calendar.getInstance().getTime());
		pm.makePersistent(entry);
		pm.close();

		return entry;
	}

	
	public boolean deleteEntry(Sheet sheet, int sequenceIndex)
			throws EntryNotFoundException {

		Entry entry = getEntryBySheetAndIndex(sheet, sequenceIndex);
		if (entry == null) {
			throw new EntryNotFoundException(String.format(
					"Entry with sheet id %s and sequence index %d not found",
					sheet.getKey().getName(), sequenceIndex));
		}

		Util.deleteEntity(entry.getKey());

		return true;
	}

	
	public List<Entry> getAllEntries() {
		Query query = new Query(Entry.class.getSimpleName());
		List<Entity> entitiesList = Util.getDatastoreServiceInstance()
				.prepare(query).asList(FetchOptions.Builder.withDefaults());

		List<Entry> entriesList = prepareEntriesList(entitiesList);

		return entriesList;
	}

	
	public List<Entry> getEntriesBySheet(Sheet sheet) {
		Query query = new Query(Entry.class.getSimpleName(), sheet.getKey());

		List<Entity> entitiesList = Util.getDatastoreServiceInstance()
				.prepare(query).asList(FetchOptions.Builder.withDefaults());
		return prepareEntriesList(entitiesList);
	}

	
	public Entry updateEntry(Entry entry) {
		PersistenceManager pm = PMF.get().getPersistenceManager();

		entry = pm.makePersistent(entry);
		pm.close();

		return entry;
	}

	
	@SuppressWarnings("deprecation")
	public Entry getEntryBySheetAndIndex(Sheet sheet, int sequenceIndex)
			throws EntryNotFoundException {
		Query query = new Query(Entry.class.getSimpleName(), sheet.getKey());
		query.addFilter("sequenceIndex", FilterOperator.EQUAL, sequenceIndex);

		Entity uniqueEntity = Util.getDatastoreServiceInstance().prepare(query)
				.asSingleEntity();

		if (uniqueEntity == null) {
			throw new EntryNotFoundException(
					String.format(
							"Entry not found in the database with sheet %s and sequence index %d",
							sheet.getKey().getName(), sequenceIndex));
		}
		System.out.println("uniqueEntity: " + uniqueEntity);
		return prepareEntry(uniqueEntity);
	}

	private List<Entry> prepareEntriesList(List<Entity> entitiesList) {
		if (entitiesList == null || entitiesList.size() == 0) {
			return null;
		}
		List<Entry> entriesList = new ArrayList<Entry>(entitiesList.size());
		for (Entity entity : entitiesList) {
			Entry entry = prepareEntry(entity);
			entriesList.add(entry);
		}
		return entriesList;
	}

	private Entry prepareEntry(Entity entity) {
		if (entity == null) {
			return null;
		}
		Entry entry = new Entry(entity.getKey(), NumUtil.getIntValue(String.valueOf(entity
				.getProperty("sequenceIndex")), -1), Character.valueOf((char)NumUtil.getIntValue(entity
						.getProperty("type"), -1)),
				NumUtil.getDoubleValue(String.valueOf(entity.getProperty("amount")),
						-1), String.valueOf(entity.getProperty("shortCode")), String.valueOf(entity
						.getProperty("description")), Character.valueOf((char)NumUtil.getIntValue(entity
						.getProperty("status"), -1)));
		try {
			entry.setSheet(sheetDAO.getSheet(entity.getKey().getParent()));
		} catch (SheetNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entry;
	}
}
