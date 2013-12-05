package org.rabbit.services.dwr.impl;

import javax.servlet.http.HttpServletRequest;

import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.rabbit.exception.EntryAlreadyExistsException;
import org.rabbit.exception.SheetNotFoundException;
import org.rabbit.model.Sheet;
import org.rabbit.services.EntryService;
import org.rabbit.services.SheetService;
import org.rabbit.services.dwr.DWREntryService;
import org.rabbit.services.dwr.vo.EntryResponseWrapper;
import org.rabbit.services.impl.EntryServiceImpl;
import org.rabbit.services.impl.SheetServiceImpl;
import org.rabbit.shared.NumUtil;
import org.rabbit.shared.ObjectUtils;

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

	public EntryResponseWrapper addMultipleEntries(String entriesInJSON) {
		
		EntryResponseWrapper entryResponseWrapper = new EntryResponseWrapper();
		
		WebContext ctx = WebContextFactory.get();
		HttpServletRequest request = ctx.getHttpServletRequest();
		String userId = request.getUserPrincipal().getName();
		
		JSONArray entriesJsonArr;
		try {
			JSONObject wrapperJSON = new JSONObject(entriesInJSON);
			String sheetKeyStr = (String)request.getSession().getAttribute("SHEET_KEY_ID");
			Sheet sheet = sheetService.getSheet(userId, sheetKeyStr);
			
			entriesJsonArr = wrapperJSON.getJSONArray("entries");
			int recordsChanged = 0;
			for (int i = 0; i < entriesJsonArr.length(); i++) {
				JSONObject entryJson = entriesJsonArr.getJSONObject(i);
				Double amount = NumUtil.getDoubleValue((String)entryJson.get(ENTRY_PROP_AMOUNT), -1);
				String shortCode = ObjectUtils.getStrValue(entryJson.get(ENTRY_PROP_SHORTCODE));
				String descr = ObjectUtils.getStrValue(entryJson.get(ENTRY_PROP_DESCR));
				String typeStr = ObjectUtils.getStrValue(entryJson.get(ENTRY_PROP_TYPE));
				char type = (typeStr.length() == 0) ? 'I': typeStr.charAt(0);
				if (ObjectUtils.isNullOrEmpty(shortCode) || ObjectUtils.isNullOrEmpty(descr) || amount <= -1) {
					continue;
				}
				entryService.addANewEntry(type, amount, shortCode, descr, 'A', sheet);
				entryResponseWrapper.setRecordsChanged(++recordsChanged);
			}
			if (recordsChanged == 0) {
				entryResponseWrapper.setErrored(true);
				entryResponseWrapper.setErrorMessage("Incomplete input data.");
			} else {
				entryResponseWrapper.setStatusMessage("New entries have been added to the sheet successfully");
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
}
