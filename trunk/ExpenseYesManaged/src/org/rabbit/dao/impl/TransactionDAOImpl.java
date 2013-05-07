package org.rabbit.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;

import org.rabbit.dao.TransactionDAO;
import org.rabbit.exception.TransactionNotFoundException;
import org.rabbit.model.Entry;
import org.rabbit.model.Transaction;
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

public class TransactionDAOImpl implements TransactionDAO {

	private TransactionDAOImpl(){
		// Do nothing in the private constructor
	}
	private static class TransactionDAOImplHandler {
		public static TransactionDAOImpl transactionDAOImpl = new TransactionDAOImpl();
	}

	public static TransactionDAOImpl getInstance() {
		return TransactionDAOImplHandler.transactionDAOImpl;
	}

	
	@Override
	public Transaction createNewTransaction(String description,
			double openingBalance, double transactionAmount, Entry entry) {

		Query query = new Query(Transaction.class.getSimpleName(),
				entry.getKey());
		query.addSort("sequenceIndex", SortDirection.DESCENDING);

		List<Entity> results = Util.getDatastoreServiceInstance()
		.prepare(query).asList(FetchOptions.Builder.withDefaults());
		
		int maxSeqIx = 0;
		if (results != null && results.size() > 0){
			Entity recentEntry = results.get(0);	
			maxSeqIx = NumUtil.getIntValue(
					recentEntry.getProperty("sequenceIndex").toString(), 0);
		}
		Key key = KeyFactory.createKey(entry.getKey(),
				Transaction.class.getSimpleName(),
				TextUtil.getEntryKeyId(entry.getKey(), ++maxSeqIx));

		Transaction transaction = new Transaction(key, openingBalance,
				description, transactionAmount, maxSeqIx);

		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.makePersistent(transaction);
		pm.close();

		return transaction;
	}

	@Override
	public void deleteTransaction(Entry entry, int sequenceIndex)
			throws TransactionNotFoundException {
		Transaction transaction = getTransactionByEntryAndIndex(entry,
				sequenceIndex);
		if (transaction == null) {
			throw new TransactionNotFoundException(
					String.format(
							"Transaction with entry id %s and sequence index %d not found",
							entry.getKey().getName(), sequenceIndex));
		}

		Util.deleteEntity(transaction.getKey());
	}

	@Override
	public List<Transaction> getAllTransactions() {
		Query query = new Query(Transaction.class.getSimpleName());
		List<Entity> entitiesList = Util.getDatastoreServiceInstance()
				.prepare(query).asList(FetchOptions.Builder.withDefaults());

		return prepareTransactionsList(entitiesList);
	}

	@Override
	public List<Transaction> getTransactionByEntry(Entry entry) {
		Query query = new Query(Transaction.class.getSimpleName(),
				entry.getKey());
		List<Entity> entitiesList = Util.getDatastoreServiceInstance()
				.prepare(query).asList(FetchOptions.Builder.withDefaults());

		return prepareTransactionsList(entitiesList);
	}

	@Override
	public Transaction getTransactionByEntryAndIndex(Entry entry,
			int sequenceIndex) throws TransactionNotFoundException {
		Query query = new Query(Transaction.class.getSimpleName(),
				entry.getKey());
		query.addFilter("sequenceIndex", FilterOperator.EQUAL, sequenceIndex);

		Entity entity = Util.getDatastoreServiceInstance().prepare(query)
				.asSingleEntity();

		if (entity == null){
			throw new TransactionNotFoundException(String.format("No transaction found with entry %s and sequence index %d", entry.getKey().getName(), sequenceIndex));
		}
		return prepareTransaction(entity);
	}

	@Override
	public Transaction updateTransaction(Transaction transaction) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		return pm.makePersistent(transaction);
	}

	private List<Transaction> prepareTransactionsList(List<Entity> entitiesList) {
		if (entitiesList == null || entitiesList.size() == 0) {
			return null;
		}

		List<Transaction> transactionsList = new ArrayList<Transaction>(
				entitiesList.size());
		for (Entity entity : entitiesList) {
			Transaction transaction = prepareTransaction(entity);
			transactionsList.add(transaction);
		}
		return transactionsList;
	}

	private Transaction prepareTransaction(Entity entity) {
		if (entity == null) {
			return null;
		}

		Transaction transaction = new Transaction(entity.getKey(),
				NumUtil.getDoubleValue(entity.getProperty("openingBalance")
						.toString(), -1), entity.getProperty("description")
						.toString(),
				NumUtil.getDoubleValue(entity.getProperty("transactionAmount")
						.toString(), -1), NumUtil.getIntValue(entity
						.getProperty("sequenceIndex").toString(), -1));

		return transaction;
	}

}
