package org.rabbit.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.rabbit.common.EntryCategory;
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
import org.rabbit.services.SheetService;
import org.rabbit.shared.ObjectUtils;
import org.rabbit.shared.ValidationUtils;
import org.rabbit.wrappers.EntryStatusWrapper;
import org.rabbit.wrappers.EntryStatusWrapperFactory;

import com.google.appengine.api.datastore.Key;

/**
 * Service layer implementation for Entry model
 * 
 * @author shanmukha.k@gmail.com <br/>
 *         for <b>Rabbit Computing, Inc.</b> <br/>
 * <br/>
 *         Date created: 02-Jun-2013
 */
public class EntryServiceImpl implements EntryService {

	private EntryServiceImpl() {
		// Do nothing
	}

	private static class EntryServiceImplHolder {
		public static EntryServiceImpl ENTRY_SERVICE_IMPL_SINGLETON_INSTANCE = new EntryServiceImpl();
	}

	public static EntryServiceImpl getInstance() {
		return EntryServiceImplHolder.ENTRY_SERVICE_IMPL_SINGLETON_INSTANCE;
	}

	private final EntryDAO entryDAO = EntryDAOImpl.getInstance();
	private final SheetDAO sheetDAO = SheetDAOImpl.getInstance();
	private final SheetService sheetService = SheetServiceImpl.getInstance();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rabbit.services.EntryService#addANewEntry(char, double,
	 * java.lang.String, java.lang.String, char,
	 * com.google.appengine.api.datastore.Key)
	 */

	public Entry addANewEntry(char type, double amount, String shortCode,
			String description, char status, Sheet sheet, String categoryLabel)
			throws EntryAlreadyExistsException {
		return entryDAO.createNewEntry(type, amount, shortCode, description,
				status, sheet, EntryCategory.getCategory(categoryLabel));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.rabbit.services.EntryService#deleteEntry(com.google.appengine.api
	 * .datastore.Key, int)
	 */

	public boolean deleteEntry(Key parentSheetKey, int sequenceIndex)
			throws EntryNotFoundException, SheetNotFoundException {
		Sheet parentSsheetForSpecifiedKey = sheetDAO.getSheet(parentSheetKey);
		if (parentSsheetForSpecifiedKey == null) {
			throw new SheetNotFoundException("Sheet doesn't exist for key: "
					+ parentSheetKey);
		}
		return entryDAO.deleteEntry(parentSsheetForSpecifiedKey, sequenceIndex);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rabbit.services.EntryService#getAllEntries()
	 */

	public List<Entry> getAllEntries() {
		return entryDAO.getAllEntries();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.rabbit.services.EntryService#getEntries(com.google.appengine.api.
	 * datastore.Key)
	 */

	public List<Entry> getEntries(Key parentSheetKey)
			throws SheetNotFoundException {
		return entryDAO.getEntriesBySheet(sheetDAO.getSheet(parentSheetKey));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.rabbit.services.EntryService#getEntryBySheetAndIndex(com.google.appengine
	 * .api.datastore.Key, int)
	 */

	public Entry getEntryBySheetAndIndex(Key parentSheetKey, int sequenceIndex)
			throws EntryNotFoundException, SheetNotFoundException {
		return entryDAO.getEntryBySheetAndIndex(
				sheetDAO.getSheet(parentSheetKey), sequenceIndex);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rabbit.services.EntryService#updateEntry(org.rabbit.model.Entry)
	 */

	public Entry updateEntry(Entry entry) {
		return entryDAO.updateEntry(entry);
	}

	public EntryStatusWrapper addMultipleEntries(String paramString,
			Map<String, String> paramMap) {
		EntryStatusWrapper localEntryStatusWrapper = EntryStatusWrapperFactory
				.getInstance().generateOne();
		Map<String, String> localHashMap = new HashMap<String, String>();
		String str1 = String.valueOf(paramMap.get("sid"));
		Sheet localSheet = null;
		char entryType = ' ';
		String entryCategory = EntryCategory.OTHERS_MISCELANEOUS.getLabel();
		String shortCode = null;
		String description = null;
		double amount = 0.0D;
		try {
			if (StringUtils.isEmpty(str1)) {
				localEntryStatusWrapper.setErrorMessage(String.format(
						"Invalid sheet identifier %s", new Object[] { str1 }));
				localEntryStatusWrapper.setRedirectURI("/list/le.jsp#content");
				localEntryStatusWrapper.setErrored(true);
				return localEntryStatusWrapper;
			}
			localSheet = this.sheetService.getSheet(paramString, str1);
			localEntryStatusWrapper.setAssociatedSheet(localSheet);
			int i = ObjectUtils.getIntValue(paramMap.get("no-of-entries"), 0);
			if (i == 0) {
				localEntryStatusWrapper.setErrorMessage(String.format(
						"Received %d number of entries for [%s %d]",
						new Object[] { Integer.valueOf(i),
								localSheet.getShortMonthStr(),
								Integer.valueOf(localSheet.getYear()) }));
				localEntryStatusWrapper.setRedirectURI(String.format(
						"/mae.jsp?sid=%s#content", new Object[] { str1 }));
				localEntryStatusWrapper.setErrored(true);
				return localEntryStatusWrapper;
			}
			if (!(ValidationUtils.doesAnyEntryHasCompleteData(i, paramMap))) {
				localEntryStatusWrapper
						.setErrorMessage(String
								.format("None of the %d entries has complete data given for [%s %d]. Both Label and Amount are required for an entry",
										new Object[] {
												Integer.valueOf(i),
												localSheet.getShortMonthStr(),
												Integer.valueOf(localSheet
														.getYear()) }));
				localEntryStatusWrapper.setRedirectURI(String.format(
						"/mae.jsp?sid=%s#content", new Object[] { str1 }));
				localEntryStatusWrapper.setErrored(true);
				return localEntryStatusWrapper;
			}
			StringBuffer localStringBuffer = new StringBuffer();
			for (int j = 1; j <= i; ++j) {
				shortCode = ObjectUtils.getStrValue(paramMap.get("shortCode_" + j));
				description = ObjectUtils.getStrValue(paramMap.get("description_" + j));
				amount = ObjectUtils.getDoubleValue(paramMap.get("amount_" + j), -1.0D);
				entryType = ObjectUtils.getStrValue(paramMap.get("type_" + j)).charAt(0);
				String tempCategory = ObjectUtils.getStrValue(paramMap.get("category_" + j));
				entryCategory = (tempCategory.length() > 0) ? tempCategory : entryCategory;
				if ((ObjectUtils.isNullOrEmpty(shortCode)) && (-1.0D == amount))
					continue;
				if (((ObjectUtils.isNotNullAndNotEmpty(shortCode)) && (-1.0D == amount))
						|| ((ObjectUtils.isNullOrEmpty(shortCode)) && (-1.0D != amount))) {
					localStringBuffer.append(
							String.format("<br/>Entry index %d is skipped due to incomplete inputs [shortCode='%s', amount='%f']",
											new Object[] { Integer.valueOf(j),
													shortCode, Double.valueOf(amount) }));
				} else {
					if ((ObjectUtils.isNullOrEmpty(str1))
							&& (ObjectUtils.isNotNullAndNotEmpty(localHashMap
									.get("SHEET_KEY_ID"))))
						str1 = ObjectUtils.getStrValue(localHashMap
								.get("SHEET_KEY_ID"));
					else if ((ObjectUtils.isNotNullAndNotEmpty(str1))
							&& (ObjectUtils.isNullOrEmpty(localHashMap
									.get("SHEET_KEY_ID"))))
						localHashMap.put("SHEET_KEY_ID", str1);
					addANewEntry(entryType, amount, shortCode, description, 'A', localSheet, entryCategory);
				}
			}
			localStringBuffer
					.insert(0,
							String.format(
									"New entries have been added to the sheet [%s %d]; details below",
									new Object[] {
											localSheet.getShortMonthStr(),
											Integer.valueOf(localSheet
													.getYear()) }));
			localEntryStatusWrapper.setStatusMessage(localStringBuffer
					.toString());
			localEntryStatusWrapper.setRedirectURI(String.format(
					"/list/le.jsp?sid=%s#content", new Object[] { str1 }));
			return localEntryStatusWrapper;
		} catch (ClassCastException localClassCastException) {
			localClassCastException.printStackTrace();
			localEntryStatusWrapper
					.setErrorMessage(String
							.format("Entry already exists with [%s %d] and other given details",
									new Object[] {
											localSheet.getShortMonthStr(),
											Integer.valueOf(localSheet
													.getYear()) }));
			localEntryStatusWrapper.setErrored(true);
		} catch (EntryAlreadyExistsException localEntryAlreadyExistsException) {
			localEntryAlreadyExistsException.printStackTrace();
			localEntryStatusWrapper
					.setErrorMessage(String
							.format("Entry already exists with [%s %d] and other given details",
									new Object[] {
											localSheet.getShortMonthStr(),
											Integer.valueOf(localSheet
													.getYear()) }));
			localEntryStatusWrapper.setErrored(true);
		} catch (IllegalArgumentException localIllegalArgumentException) {
			localIllegalArgumentException.printStackTrace();
			localEntryStatusWrapper
					.setErrorMessage(localIllegalArgumentException.getMessage());
			localEntryStatusWrapper.setErrored(true);
		} catch (SheetNotFoundException localSheetNotFoundException) {
			localSheetNotFoundException.printStackTrace();
			localEntryStatusWrapper.setErrorMessage(localSheetNotFoundException
					.getMessage());
			localEntryStatusWrapper.setErrored(true);
		}
		localEntryStatusWrapper.setRedirectURI(String.format(
				"/mae.jsp?sid=%s#content", new Object[] { str1 }));
		return localEntryStatusWrapper;
	}

	@Override
	public Entry addANewEntry(char type, double amount, String shortCode,
			String description, char status, Sheet parentSheet)
			throws EntryAlreadyExistsException {
		return addANewEntry(type, amount, shortCode, description, status, parentSheet, EntryCategory.OTHERS_MISCELANEOUS.getLabel());
	}
}
