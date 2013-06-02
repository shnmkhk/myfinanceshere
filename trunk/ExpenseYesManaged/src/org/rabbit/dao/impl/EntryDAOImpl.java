package org.rabbit.dao.impl;

import java.util.ArrayList;
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
import org.rabbit.shared.TextUtil;
import org.rabbit.shared.Util;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
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

	@Override
	public Entry createNewEntry(char type, double amount, String shortCode,
			String description, char status, Sheet sheet)
			throws EntryAlreadyExistsException {

		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = new Query(Entry.class.getSimpleName(), sheet.getKey());
		query.addSort("sequenceIndex", SortDirection.DESCENDING);

		Entity recentEntry = Util.getDatastoreServiceInstance().prepare(query)
				.asSingleEntity();
		int maxSeqIx = 0;
		if (recentEntry != null) {
			maxSeqIx = NumUtil.getIntValue(
					recentEntry.getProperty("sequenceIndex").toString(), 0);
		}
		Key key = KeyFactory.createKey(sheet.getKey(),
				Entry.class.getSimpleName(),
				TextUtil.getEntryKeyId(sheet.getKey(), ++maxSeqIx));

		Entry entry = new Entry(key, maxSeqIx, type, amount, shortCode,
				description, status);

		pm.makePersistent(entry);
		pm.close();

		return entry;
	}

	@Override
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

	@Override
	public List<Entry> getAllEntries() {
		Query query = new Query(Entry.class.getSimpleName());
		List<Entity> entitiesList = Util.getDatastoreServiceInstance()
				.prepare(query).asList(FetchOptions.Builder.withDefaults());

		List<Entry> entriesList = prepareEntriesList(entitiesList);

		return entriesList;
	}

	@Override
	public List<Entry> getEntriesBySheet(Sheet sheet) {
		Query query = new Query(Entry.class.getSimpleName(), sheet.getKey());

		List<Entity> entitiesList = Util.getDatastoreServiceInstance()
				.prepare(query).asList(FetchOptions.Builder.withDefaults());
		return prepareEntriesList(entitiesList);
	}

	@Override
	public Entry updateEntry(Entry entry) {
		PersistenceManager pm = PMF.get().getPersistenceManager();

		entry = pm.makePersistent(entry);
		pm.close();

		return entry;
	}

	@Override
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
		Entry entry = new Entry(entity.getKey(), NumUtil.getIntValue(entity
				.getProperty("sequenceIndex").toString(), -1), entity
				.getProperty("type").toString().charAt(0),
				NumUtil.getDoubleValue(entity.getProperty("amount").toString(),
						-1), entity.getProperty("shortCode").toString(), entity
						.getProperty("description").toString(), entity
						.getProperty("status").toString().charAt(0));
		try {
			entry.setSheet(sheetDAO.getSheet(entity.getKey().getParent()));
		} catch (SheetNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entry;
	}
}
