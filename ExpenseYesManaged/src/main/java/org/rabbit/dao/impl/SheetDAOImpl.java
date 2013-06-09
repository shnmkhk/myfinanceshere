package org.rabbit.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;

import org.rabbit.dao.SheetDAO;
import org.rabbit.exception.SheetAlreadyExistsException;
import org.rabbit.exception.SheetNotFoundException;
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

/**
 * DAO layer loading/ persisting implementation for Sheet entity
 * 
 * @author shanmukha.k@gmail.com <br/>
 * for <b>Rabbit Computing, Inc.</b> <br/><br/> 
 * Date created: 01-May-2013
 */
public class SheetDAOImpl implements SheetDAO {

	private SheetDAOImpl(){
		// Do nothing in the private constructor
	}
	private static class SheetDAOImplHandler {
		public static SheetDAOImpl sheetDAOImpl = new SheetDAOImpl();
	}

	public static SheetDAOImpl getInstance() {
		return SheetDAOImplHandler.sheetDAOImpl;
	}

	
	@Override
	public Sheet createNewSheet(int month, int year)
			throws SheetAlreadyExistsException {

		Sheet sheet = null;
		try {
			sheet = getSheet(month, year);
			throw new SheetAlreadyExistsException(String.format("Sheet with month %d and year %d already exists", month, year));
		} catch (SheetNotFoundException e1) {
			// Ignore if it throws Sheet not found, it is expected
		}
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Key key = KeyFactory.createKey(null, Sheet.class.getSimpleName(),
				TextUtil.getSheetKeyId(month, year));
		sheet = new Sheet(key, month, year);
		
		pm.makePersistent(sheet);
		sheet = pm.detachCopy(sheet);
		pm.close();
		
		return sheet;
	}

	@Override
	public void deleteSheet(int month, int year) throws SheetNotFoundException {
		Sheet sheet = getSheet(month, year);
		if (sheet == null) {
			throw new SheetNotFoundException(
					String.format(
							"Sheet with month %d and year %d is not found",
							month, year));
		}
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.deletePersistent( pm.getObjectById( Sheet.class,
				TextUtil.getSheetKeyId(month, year) ) ); 
		
		pm.close();
	}

	@Override
	public List<Sheet> getAllSheets() {

		Query query = new Query(Sheet.class.getSimpleName());
		List<Entity> list = Util.getDatastoreServiceInstance().prepare(query)
				.asList(FetchOptions.Builder.withDefaults());

		List<Sheet> sheetsList = new ArrayList<Sheet>(list.size());
		for (Entity e : list) {
			Sheet sheet = new Sheet(e.getKey(), NumUtil.getIntValue(e
					.getProperty("month").toString(), -1), NumUtil.getIntValue(
					e.getProperty("year").toString(), -1));

			sheetsList.add(sheet);
		}

		return sheetsList;
	}

	@Override
	public Sheet getSheet(int month, int year) throws SheetNotFoundException {
		Key key = KeyFactory.createKey(Sheet.class.getSimpleName(),
				TextUtil.getSheetKeyId(month, year));

		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Sheet sheet = pm.getObjectById(Sheet.class, key);
			pm.close();
			return sheet;
		} catch (JDOObjectNotFoundException nonfe) {
			throw new SheetNotFoundException(String.format("No sheet found with month %d and year %d", month, year));
		}
	}
	
	@Override
	public Sheet getSheet(Key sheetKey) throws SheetNotFoundException {

		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Sheet sheet = pm.getObjectById(Sheet.class, sheetKey);
			pm.close();
			return sheet;
		} catch (JDOObjectNotFoundException nonfe) {
			throw new SheetNotFoundException(String.format("No sheet found with key %s ", sheetKey.getName()));
		}
	}
}
