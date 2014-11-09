package org.rabbit.services.dwr.impl;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.rabbit.exception.EntryAlreadyExistsException;
import org.rabbit.exception.EntryNotFoundException;
import org.rabbit.exception.SheetNotFoundException;
import org.rabbit.model.Sheet;
import org.rabbit.services.EntryService;
import org.rabbit.services.SheetService;
import org.rabbit.services.dwr.DWREntryService;
import org.rabbit.services.dwr.vo.EntryResponseWrapper;
import org.rabbit.services.impl.EntryServiceImpl;
import org.rabbit.services.impl.SheetServiceImpl;
import org.rabbit.shared.ObjectUtils;
import org.rabbit.shared.TextUtil;

import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class DWREntryServiceImpl implements DWREntryService {
	private EntryService entryService = EntryServiceImpl.getInstance();
	private SheetService sheetService = SheetServiceImpl.getInstance();

	public static final String ENTRY_PROP_AMOUNT = "amount";
	public static final String ENTRY_PROP_SHORTCODE = "shortCode";
	public static final String ENTRY_PROP_DESCR = "descr";
	public static final String ENTRY_PROP_TYPE = "type";
	public static final String ENTRY_PROP_CATEGORY = "category";
	public static final String ENTRY_PROP_SEQ_IX = "seq_ix";
	public static final String ENTRY_PROP_SHEET_KEY_STR = "sheet_key_str";

	public EntryResponseWrapper deleteSelectedEntries(String entriesInJSON) {

		EntryResponseWrapper deleteResponseWrapper = new EntryResponseWrapper();

		WebContext ctx = WebContextFactory.get();
		HttpServletRequest request = ctx.getHttpServletRequest();
		String userId = request.getUserPrincipal().getName();

		JSONArray toDeleteJSONArr;
		int recordsChanged = 0;
		try {
			JSONObject wrapperJSON = new JSONObject(entriesInJSON);
			String sheetKeyStr = (String) request.getSession().getAttribute("SHEET_KEY_ID");
			String sheetKeyIdFromPage = (String)wrapperJSON.get(ENTRY_PROP_SHEET_KEY_STR);
			if (ObjectUtils.isNotNullAndNotEmpty(sheetKeyIdFromPage)) {
				sheetKeyStr = sheetKeyIdFromPage;
			}
			toDeleteJSONArr = wrapperJSON.getJSONArray("entries");
			Sheet sheet = sheetService.getSheet(userId, sheetKeyStr);
			boolean deletedSuccess = false;
			for (int i = 0; i < toDeleteJSONArr.length(); i++) {
				JSONObject toDeleteEntry = toDeleteJSONArr.getJSONObject(i);
				int toDeleteSeqIx = toDeleteEntry.getInt(ENTRY_PROP_SEQ_IX);
				deletedSuccess = entryService.deleteEntry(sheet.getKey(), toDeleteSeqIx);
				if (deletedSuccess) {
					deleteResponseWrapper.setRecordsChanged(++recordsChanged);
				}
			}
			
			if (recordsChanged == 0) {
				deleteResponseWrapper.setErrored(true);
				deleteResponseWrapper.setErrorMessage("One or more deletion of entries went unsuccessful, try again.");
			}
		} catch (JSONException e) {
			e.printStackTrace();
			deleteResponseWrapper.setErrored(true);
			deleteResponseWrapper.setErrorMessage(e.getMessage());

		} catch (EntryNotFoundException e) {
			e.printStackTrace();
			deleteResponseWrapper.setErrored(true);
			deleteResponseWrapper.setErrorMessage(e.getMessage());
		} catch (SheetNotFoundException e) {
			e.printStackTrace();
			deleteResponseWrapper.setErrored(true);
			deleteResponseWrapper.setErrorMessage(e.getMessage());
		}

		return deleteResponseWrapper;
	}

	public EntryResponseWrapper addMultipleEntries(String entriesInJSON) {

		EntryResponseWrapper entryResponseWrapper = new EntryResponseWrapper();

		WebContext ctx = WebContextFactory.get();
		HttpServletRequest request = ctx.getHttpServletRequest();
		String userId = request.getUserPrincipal().getName();

		JSONArray entriesJsonArr;
		try {
			JSONObject wrapperJSON = new JSONObject(entriesInJSON);
			String sheetKeyStr = (String) request.getSession().getAttribute(
					"SHEET_KEY_ID");
			Sheet sheet = sheetService.getSheet(userId, sheetKeyStr);

			entriesJsonArr = wrapperJSON.getJSONArray("entries");
			int recordsChanged = 0;
			for (int i = 0; i < entriesJsonArr.length(); i++) {
				JSONObject entryJson = entriesJsonArr.getJSONObject(i);
				double amount = -1;
				try {
					amount = TextUtil.nf.parse((String)entryJson.get(ENTRY_PROP_AMOUNT)).doubleValue();
				} catch (ParseException e) {
				}
				String shortCode = ObjectUtils.getStrValue(entryJson.get(ENTRY_PROP_SHORTCODE));
				String descr = ObjectUtils.getStrValue(entryJson.get(ENTRY_PROP_DESCR));
				String typeStr = ObjectUtils.getStrValue(entryJson.get(ENTRY_PROP_TYPE));
				char type = (typeStr.length() == 0) ? 'I' : typeStr.charAt(0);
				String category = ObjectUtils.getStrValue(entryJson.get(ENTRY_PROP_CATEGORY));
				if (ObjectUtils.isNullOrEmpty(shortCode) || ObjectUtils.isNullOrEmpty(descr) || amount <= -1 || 
						ObjectUtils.isNullOrEmpty(category)) {
					continue;
				}
				entryService.addANewEntry(type, amount, shortCode, descr, 'A', sheet, category);
				entryResponseWrapper.setRecordsChanged(++recordsChanged);
			}
			if (recordsChanged == 0) {
				entryResponseWrapper.setErrored(true);
				entryResponseWrapper.setErrorMessage("Incomplete input data.");
			} else {
				entryResponseWrapper
						.setStatusMessage("New entries have been added to the sheet successfully");
				entryResponseWrapper.setResponseAsString("SUCCESS");
			}
		} catch (JSONException e) {
			e.printStackTrace();
			entryResponseWrapper.setErrored(true);
			entryResponseWrapper.setErrorMessage(e.getMessage());
		} catch (EntryAlreadyExistsException e) {
			e.printStackTrace();
			entryResponseWrapper.setErrored(true);
			entryResponseWrapper.setErrorMessage(e.getMessage());
		} catch (SheetNotFoundException e) {
			e.printStackTrace();
			entryResponseWrapper.setErrored(true);
			entryResponseWrapper.setErrorMessage(e.getMessage());
		}

		return entryResponseWrapper;
	}

	public EntryResponseWrapper getPiChartDataForGivenSheet(String sheetIdJSON) {
		// TODO Auto-generated method stub
		return null;
	}
}