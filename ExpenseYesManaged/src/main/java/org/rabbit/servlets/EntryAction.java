package org.rabbit.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rabbit.exception.EntryAlreadyExistsException;
import org.rabbit.exception.EntryNotFoundException;
import org.rabbit.exception.SheetNotFoundException;
import org.rabbit.model.Entry;
import org.rabbit.model.Sheet;
import org.rabbit.shared.NumUtil;
import org.rabbit.shared.ObjectUtils;
import org.rabbit.shared.RequestUtil;

import com.google.appengine.api.datastore.KeyFactory;

public class EntryAction extends BaseServlet {

	private static final long serialVersionUID = -9092072935123090022L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String baseHref = handleCancelAndReturnBaseHref(request, response);
		String[] uriFragments = request.getRequestURI().substring(1).split("/");
		Sheet selectedSheet = null;
		List<Entry> listOfEntries = null;
		if (uriFragments.length == 1){
			String sheetKeyId = (String) request.getSession().getAttribute("SHEET_KEY_ID");
			if (ObjectUtils.isNotNullAndNotEmpty(sheetKeyId)) {
				try {
					Sheet sheet = sheetService.getSheet(sheetKeyId);
					RequestUtil.refreshAllEntriesOfGivenSheetIntoSession(request, sheet);
				} catch (SheetNotFoundException e) {
					e.printStackTrace();
				}
				response.sendRedirect(baseHref + "/list/le.jsp#content");
			} else {
				// No sheet key id found in the session, so redirecting it to list sheets
				request.getSession().setAttribute("ERROR_MESSAGE", String.format("Please select a sheet"));
				response.sendRedirect(baseHref + "/sa/#content");
			}
		} else if (uriFragments.length == 2){
			// It is only sheet key.
			String sheetKeyId = uriFragments[1];
			int[] monthYr = ObjectUtils.getMonthYr(sheetKeyId);
			try {
				listOfEntries = entryService.getEntries(KeyFactory.createKey(Sheet.class.getSimpleName(), sheetKeyId));
			} catch (SheetNotFoundException e) {
				System.err.println(e.getMessage());
				request.getSession().setAttribute("ERROR_MESSAGE", String.format("Sheet doesn't exist with month - <b>%d</b> and year - <b>%d</b>", monthYr[0], monthYr[1]));
				RequestUtil.refreshAllSheetsIntoSession(request);
				response.sendRedirect(baseHref + "/list/ls.jsp#content");
				return;
			}
			
			request.getSession().setAttribute("SHEET_KEY_ID", sheetKeyId);
			Sheet sheet;
			try {
				sheet = sheetService.getSheet(sheetKeyId);
				RequestUtil.refreshAllEntriesOfGivenSheetIntoSession(request, sheet);
			} catch (SheetNotFoundException e) {
				e.printStackTrace();
			}
			response.sendRedirect(baseHref + "/list/le.jsp#content");
		} else if (uriFragments.length > 2){
			String sheetKeyId = ObjectUtils.getStrValue(uriFragments[1]);
			int entrySequence = ObjectUtils.getIntValue(uriFragments[2], NumUtil.MINUS_ONE);
			if (entrySequence != NumUtil.MINUS_ONE){
				try {
					boolean recordDeleted =
							entryService.deleteEntry(KeyFactory.createKey(Sheet.class.getSimpleName(), sheetKeyId), entrySequence);
					if (recordDeleted) {
						RequestUtil.refreshAllEntriesOfGivenSheetIntoSession(request, sheetService.getSheet(sheetKeyId));
						request.getSession().setAttribute("INFO_MESSAGE", String.format("Entry deleted successfully with sheetKey %s and sequence %d", sheetKeyId, entrySequence));
					} else {
						request.getSession().setAttribute("ERROR_MESSAGE", String.format("Entry deletion failed with sheetKey %s and sequence %d", sheetKeyId, entrySequence));						
					}
					response.sendRedirect(baseHref + "/list/le.jsp#content");
				} catch (EntryNotFoundException e) {
					request.getSession().setAttribute("ERROR_MESSAGE", String.format("Entry not found with sheet key %s and sequence %d", sheetKeyId, entrySequence));
					response.sendRedirect(baseHref + "/error.jsp#content");
					e.printStackTrace();
				} catch (SheetNotFoundException e) {
					request.getSession().setAttribute("ERROR_MESSAGE", String.format("Sheet not found with sheet key %s", sheetKeyId));
					response.sendRedirect(baseHref + "/error.jsp#content");
					e.printStackTrace();
				}
			}
			
		} else {
			request.getSession().setAttribute("ERROR_MESSAGE", String.format("Invalid number of arguments in the RequestedURI %s", request.getRequestURI()));
			response.sendRedirect(baseHref + "/error.jsp#content");
			return;
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String baseHref = handleCancelAndReturnBaseHref(request, response);
		String submit = RequestUtil.getStringValue(request, "submit");
		if (RequestUtil.EMPTY_STR.equals(submit) || RequestUtil.CANCEL_STR.equalsIgnoreCase(submit)) {
			response.sendRedirect(baseHref + "/list/le.jsp#content");
			return;
		}

		String shortCode = RequestUtil.getStringValue(request, "shortCode");
		String description = RequestUtil.getStringValue(request, "description");
		double amount = RequestUtil.getDoubleValue(request, "amount", NumUtil.MINUS_ONE);
		String sheetKeyStr = RequestUtil.getStringValue(request, "sid");
		char type = RequestUtil.getStringValue(request, "type").charAt(0);
		
		if (ObjectUtils.isNullOrEmpty(shortCode) || ObjectUtils.isNullOrEmpty(amount)) {
			if (NumUtil.MINUS_ONE != amount){
				request.getSession().setAttribute("INPUT_AMOUNT", amount);
			} 
			if (RequestUtil.EMPTY_STR.equals(shortCode)) {
				request.getSession().setAttribute("INPUT_SHORT_CODE", shortCode);
			}
			request.getSession().setAttribute("ERROR_MESSAGE", String.format("Invalid Amount or Label"));
			response.sendRedirect(baseHref + "/ae.jsp#content");
			return;
		}
		
		if (ObjectUtils.isNullOrEmpty(sheetKeyStr) && ObjectUtils.isNullOrEmpty(request.getSession().getAttribute("SHEET_KEY_ID"))){
			sheetKeyStr = ObjectUtils.getStrValue(request.getSession().getAttribute("SHEET_KEY_ID"));
		}
		Sheet sheet = null;
		try {
			sheet = sheetService.getSheet(sheetKeyStr);
			entryService.addANewEntry(type, amount, shortCode, description, 'A', sheet);
			request.getSession().setAttribute("INFO_MESSAGE", String.format("Added a new entry for sheet %s", sheetKeyStr));
			
			RequestUtil.refreshAllEntriesOfGivenSheetIntoSession(request, sheet);
			response.sendRedirect(baseHref + "/list/le.jsp?sid=" + sheetKeyStr + "#content");
			return;
		} catch (EntryAlreadyExistsException e) {
			System.err.println(e.getMessage());
			request.getSession().setAttribute("ERROR_MESSAGE", String.format("Entry already exists with %s and other given details", sheetKeyStr));
		} catch (IllegalArgumentException ie) {
			System.err.println(ie.getMessage());
			request.getSession().setAttribute("ERROR_MESSAGE", ie.getMessage());
		} catch (SheetNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}